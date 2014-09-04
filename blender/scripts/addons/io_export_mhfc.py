# ##### BEGIN GPL LICENSE BLOCK #####
#
#  This program is free software; you can redistribute it and/or
#  modify it under the terms of the GNU General Public License
#  as published by the Free Software Foundation; either version 2
#  of the License, or (at your option) any later version.
#
#  This program is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#  GNU General Public License for more details.
#
#  You should have received a copy of the GNU General Public License
#  along with this program; if not, write to the Free Software Foundation,
#  Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
#
# ##### END GPL LICENSE BLOCK #####

bl_info = {
	"name": "Export: MHFC (.mhmd)",
	"description": "Export monster to the Minecraft mod MFHC",
	"author": "Martin Molzer",
	"version": (0, 3),
	"blender": (2, 70, 0),
	"location": "File > Export > Monster Hunter Frontier Craft (.mhmd)",
	"warning": "",
	"wiki_url": "",
	"tracker_url": "",
	"category": "Import-Export",
	}

from bpy.app.handlers import persistent
from bpy.props import BoolProperty, CollectionProperty, EnumProperty, IntProperty, IntVectorProperty, PointerProperty, StringProperty
from bpy.types import Mesh, Operator, Panel, PropertyGroup, Scene, UIList, Menu, Header, AddonPreferences, Action
from mathutils import Matrix
from bpy_extras.io_utils import ExportHelper
from collections import defaultdict
import bmesh
import bpy
import os
import random
import re
import struct



class FatalException(RuntimeError):
	pass

class ErrorException(RuntimeError):
	pass

def fatal(message, *args, cause=None, **wargs):
	"""
	When something happened that really shouldn't happen.
	"""
	formatted = message.format(*args, **wargs)
	raise FatalException("This should not have happened. Report to WorldSEnder:\n{mess}".format(mess=formatted)) from cause

def error(message, *args, cause=None, **wargs):
	"""
	When something happened that can't conform with the specification.
	"""
	formatted = message.format(*args, **wargs)
	raise ErrorException(formatted) from cause

def warning(message, *args, **wargs):
	formatted = message.format(*args, **wargs)
	warning.active_op.report({'WARNING'}, formatted)

def extract_safe(collection, item, mess_on_fail, *args, **wargs):
	"""
	Ensures that the item is in the collection by raising
	a fatal error with the specified message if not
	"""
	try:
		return collection[item]
	except KeyError:
		error(mess_on_fail, *args, **wargs)

def write_string(string, file_h):
	"""
	Writes a String to a file
	"""
	file_h.write(string.encode("utf-8") + b'\x00')

def write_packed(fmt, file_h, *args):
	"""
	Packs the given data into the given bytebuffer using the given format
	"""
	file_h.write(struct.pack(fmt, *args))

def to_valid_loc(assetstr):
	return assetstr.replace(' ', '_')

def asset_to_dir(assetstr):
	"""
	Translates and minecraft asset string to a filesystem path
	"""
	if not assetstr:
		error("Asset-String can't be empty")
	vals = assetstr.split(':')
	if len(vals) == 1:
		return "assets/minecraft/" + assetstr
	elif len(vals) == 2:
		if not vals[0] or not vals[1]:
			error("Asset-String {loc}: Splitted string mustn't be empty".format(loc=assetstr))
		return "assets/{mod}/{file}".format(mod=vals[0], file=vals[1])
	else:
		error("Asset-String {loc} can't contain more than one ':'".format(loc=assetstr))

def openw_save(filepath, flags, *args, **wargs):
	"""
	Ensures that the directory for the filepath exists and creates it if
	necessary. Returns a file_handle to the open stream by calling
	open(filepath, flags, *args, **wargs)
	"""
	filepath = bpy.path.abspath(filepath)
	dir = os.path.dirname(filepath)
	if not os.path.exists(dir):
		os.makedirs(dir)
	return open(filepath, flags, *args, **wargs)

def exportv1(context, op, file_h):
	# write version
	write_packed(">I", file_h, 1)
	class Bone(object):
		def __init__(self, bone):
			"""
			bone Bone: can't be null, the bone this is initialized from
			"""
			if bone is None:
				fatal("(Bone) bone is None")
			self.name = bone.name
			mat = bone.matrix_local
			if bone.parent is not None:
				# this is a little quick fix we have to do, don't ask me...
				mat = bone.parent.matrix_local.inverted() * mat
			self.quat = mat.to_quaternion()
			self.translate = mat.translation
		def to_bytes(self, file_h):
			q = self.quat
			t = self.translate
			write_string(self.name, file_h)
			write_packed(">7f", file_h, q.x, q.y, q.z, q.w,
									  t.x, t.y, t.z)
	class Point(object):
		def __init__(self, loop, uv_layer, deform_layer, arm_vgroup_idxs):
			"""
			loop Loop: Can't be none, the loop this points represents
			uv_layer LLayerItem: Can't be None, the uv_layer we export
			deform_layer VLayerItem: Can be None, indicates that no bindings
									 should be exported
			arm_vgroup_idxs list: Can't be None, value at index i represents the index of the
								  arm.bones[i]'s vertex group in object
			"""
			if loop is None:
				fatal("(Point) Loop is None")
			vtx = loop.vert
			self.coords = vtx.co
			self.normal = vtx.normal
			self.uv = loop[uv_layer].uv
			self.bindings = []
			# calculate bindings
			if deform_layer is None:
				return
			def insert_into_binds(bind):
				"""
				bind tuple: Can't be None, (idx, value) of binding
				"""
				_b_idx, b_value = bind
				if bind is None:
					fatal("(Point) Inserted tupple is None")
				for idx, (_ex_idx, ex_value) in enumerate(self.bindings):
					if b_value > ex_value:
						self.bindings.insert(idx, existing)
						break
				self.bindings.append(bind)
			# iterate over all bones (as indices)
			for idx, vgroup_idx in enumerate(arm_vgroup_idxs):
				if vgroup_idx == -1:
					# bone has no vertex group
					continue
				try:
					value = vtx[deform_layer][vgroup_idx]
				except KeyError:
					# no binding to this bone
					pass
				else:
					if value > 0:
						insert_into_binds((idx, value))
		def to_bytes(self, file_h):
			binds = self.bindings[0:4]
			co = self.coords
			norm = self.normal
			uv = self.uv
			write_packed(
					">8f" + "Bf" * len(binds),
					file_h,
					co.x, co.y, co.z,
					norm.x, norm.y, norm.z,
					uv.x, 1 - uv.y,
					*[v for bind in binds for v in bind])
			if len(binds) < 4:
				file_h.write(b'\xFF')
		def __eq__(self, other):
			epsilon = 0.001
			return  (self.bindings == other.bindings and
					(self.uv - other.uv).length < epsilon and
					(self.coords - other.coords).length < epsilon and
					(self.normal - other.normal).length < epsilon)
		def __str__(self):
			return "Point(co={co}, normal={nrom}, uv={uv}, bindings={binds})".format(co=self.coords, norm=self.normal, uv=self.uv, binds=self.bindings)
	class Part(object):
		def __init__(self, group):
			"""
			group Group: can be None
			"""
			img = op.default_img
			if group is None:
				self.name = op.default_group_name
			elif group.image in bpy.data.images:
				self.name = group.ident
				img = group.image
			else:
				self.name = group.ident
				warning("Group {name} has an invalid image ({img}) assigned. Defaulted to {default}",
						name=self.name, img=group.image, default=op.default_img)
			self.image = img
			self.img_location = to_valid_loc(
					tex_path.format(
							modid=mod_id,
							modelname=model_name,
							texname=img))
			# maps vertices to (point, list-index) pair-list
			self.point_map = defaultdict(lambda: [])
			# a full list of all points
			self.points = []
			self.indices = []
		def append_face(self, face, uv_layer, deform_layer, arm_vgroup_idxs):
			"""
			uv_layer LLayerItem: Can't be None, the uv_layer we export
			deform_layer VLayerItem: Can be None, indicates that no bindings
									 should be exported
			arm_vgroup_idxs list: Can't be None, value at index i represents the index of the
								  arm.bones[i]'s vertex group in object
			"""
			def key_from_loop(l):
				return l.vert.index
			for loop in face.loops:
				key = key_from_loop(loop)
				# append points or reuse existant one's
				new_point = Point(loop, uv_layer, deform_layer, arm_vgroup_idxs)
				index = -1
				known_points = self.point_map[key]
				for point, idx in known_points:
					if point == new_point:
						index = idx
						break
				else:
					index = len(self.points)
					self.points.append(new_point)
					known_points.append((new_point, index))
				assert index >= 0
				self.indices.append(index)
		def to_bytes(self, file_h):
			points = self.points
			p_len = len(self.points)
			idxs = self.indices
			idxs_len = len(idxs)
			if idxs_len % 3:
				fatal("(Part) number of indices not divisible by 3")
			tris = idxs_len//3
			if tris >= 2**16:
				error("(Part) too many tris in part {name}", name=self.name)
			write_packed(">2H", file_h, p_len, tris)
			write_string(self.name, file_h)
			write_string(self.img_location, file_h)
			for point in points:
				point.to_bytes(file_h)
			write_packed(">{idxs}H".format(idxs=idxs_len), file_h, *idxs)
	# extract used values
	obj = bpy.data.objects[op.object]
	mod_id = op.mod_id
	tex_path = op.tex_path
	model_name = op.model_name
	# the object we export
	bm = bmesh.new()
	if context.mode == 'EDIT_MESH':
		bm = bmesh.from_edit_mesh(obj.data).copy()
	else:
		bm.from_mesh(obj.data)
	bmesh.ops.triangulate(bm, faces=bm.faces)
	# the armature to that object
	arm = None
	if op.armature in bpy.data.objects:
		arm = bpy.data.objects[op.armature]
	# for each material we make one part
	part_dict = {}
	# checked
	uv_layer = bm.loops.layers.uv[op.uv_layer]
	# could be None
	deform_layer = bm.verts.layers.deform.active
	# never none
	arm_vgroup_idxs = ([] if arm is None else
					   [obj.vertex_groups.find(bone.name) for bone in arm.data.bones])
	g_layer = None
	if 'MHFCRenderGroupIndex' in bm.faces.layers.int:
		g_layer = bm.faces.layers.int['MHFCRenderGroupIndex']
	groups = obj.data.mhfcprops.render_groups
	for face in bm.faces:
		g_idx = -1
		if g_layer is not None:
			g_idx = face[g_layer] - 1
		group = None
		if g_idx >= 0 and g_idx < len(groups):
			group = groups[g_idx]
		if group not in part_dict:
			part_dict.update({group: Part(group)})
		part_dict[group].append_face(face, uv_layer, deform_layer, arm_vgroup_idxs)
	bones = ([] if arm is None else
			 [Bone(bone) for bone in arm.data.bones])
	bone_parents = ([] if arm is None else
					[arm.data.bones.find(b.parent.name) & 0xFF if b.parent is not None else 255 for b in arm.data.bones])
	if len(part_dict) > 255:
		error("Too many parts")
	if len(bones) > 255:
		error("Too many bones")
	write_packed(">2B", file_h, len(part_dict), len(bones))
	for key in part_dict.keys():
		part = part_dict[key]
		part.to_bytes(file_h)
	for bone in bones:
		bone.to_bytes(file_h)
	write_packed(">{nums}B".format(nums=len(bones)), file_h, *bone_parents)
	bm.free()
	if op.export_tex:
		settings = context.scene.render.image_settings
		settings.file_format = 'PNG'
		settings.color_mode = 'RGBA'
		for img, path in set([(p.image, p.img_location) for p in part_dict.values()]):
			ext_path =\
					os.path.join(
							op.directory,
							asset_to_dir(path))
			bpy.data.images[img].save_render(ext_path, scene=context.scene)
		
	op.report({'INFO'}, 'Exported with ({numparts} parts, {numbones} bones)'.format(numparts=len(part_dict), numbones=len(bones)))

# exporters also have to write their version number
known_exporters = {"V1": exportv1}

def export_selected(context, op):
	scene = context.scene
	# Write file header
	bitmask = 0xFFFFFFFF
	uuid_vec = scene.mhfcprops.uuid
	# Write to file
	modelpath = op.model_path.format(modid=op.mod_id, modelname=op.model_name)
	filepath = os.path.join(op.directory, asset_to_dir(modelpath))
	with openw_save(filepath, 'wb') as file_h:
		write_packed(">Q4I", file_h, 0x4d484643204d444c, uuid_vec[0] & bitmask,
													   uuid_vec[1] & bitmask,
													   uuid_vec[2] & bitmask,
													   uuid_vec[3] & bitmask)
		write_string(op.artist, file_h)
		method = known_exporters[op.version]
		try:
			method(context, op, file_h)
		except NotImplementedError:
			fatal("Version {v} is not implemented yet", v=op.version)
	return {'FINISHED'}

# helper methods for properties
def get_prop(name, generator, init_name=None):
	"""
	This generates a new value if necessary before returning it.
	name: the properties own name
	generator: a function that takes two parameters:
			the object it is called on (e.g. the scene)
			the identifier of the value
			returns - the new value
	init_name: the name of a value that tells if this value
			initialized. If not init_name: returns a function
			that checks against the actual value
	"""
	def get(self):
		if not getattr(self, init_name):
			self[name] = generator(self, name)
			setattr(self, init_name, True)
		return self[name]
	return get

def rand_gen(obj, name):
	var = lambda: random.randint(-2**31, 2**31-1)
	return (var(), var(), var(), var())

def default_ifnot(property, default):
	def update(self, context):
		if not getattr(self, property):
			setattr(self, property, default)
	if not default:
		raise ValueError("Default must test True for 'if default' to ensure no recursion occurs.")
	return update

class Preferences(AddonPreferences):
	bl_idname = __name__
	
	mod_id = StringProperty(
			name="Mod ID",
			description="Your Mod ID",
			default="mhfc",
			update=default_ifnot('mod_id', "mhfc"),
			options={'HIDDEN'})
	directory = StringProperty(
			name="Dir name",
			description="The folder to export to",
			subtype='DIR_PATH',
			options={'HIDDEN'})
	model_path = StringProperty(
			name="Modelpath",
			description="A formatstring to the path of you model. You may use {modid} and {modelname}.",
			default="{modid}:models/{modelname}/{modelname}.mhmd",
			update=default_ifnot('model_path', "{modid}:models/{modelname}/{modelname}.mhmd"),
			options={'HIDDEN'})
	tex_path = StringProperty(
			name="Texpath",
			description="A formatstring to the textures. You may use {modid}, {modelname} and {texname}.",
			default="{modid}:textures/models/{modelname}/{texname}.png",
			update=default_ifnot('tex_path', "{modid}:textures/models/{modelname}/{texname}.png"),
			options={'HIDDEN'})
	
	def draw(self, context):
		layout = self.layout
		layout.prop(self, 'mod_id')
		layout.prop(self, 'directory')
		layout.prop(self, 'model_path')
		layout.prop(self, 'tex_path')

class ArmatureDESCR(PropertyGroup):
	name = StringProperty(name="Armature Name")

class RenderGroups(PropertyGroup):
	def update(self, context):
		# make the assigned value unique
		value = self.ident
		props = getattr(self.id_data.mhfcprops, 'render_groups')
		for other in [val for val in props if val.ident == value]:
			if other == self:
				return
			match = re.match(".*\.(\d{3,})", value)
			stem, val = match is None and (value, 1) or (value[:-4], int(value[-3:]))
			while "{s}.{v:0>#03}".format(s=stem, v=val) in [val.ident for val in props]:
				val += 1
			other.ident = "{s}.{v:0>#03}".format(s=stem, v=val)
	ident = StringProperty(
			name="Name",
			description="The name of this group",
			default="Default",
			update=update)
	image = StringProperty(
			name="Image",
			description="The image-texture of this group")

class SceneProps(PropertyGroup):
	isuuidset = BoolProperty(
			name="isUUIDset",
			description="Set to true if scene data is initialized. DON'T TOUCH THIS UNLESS YOU ARE SURE WHAT YOU DO.",
			default=False,
			options=set())
	uuid = IntVectorProperty(
			name="UUID",
			description="An unique ID for this file. Read-only",
			options=set(),
			default=(0, 0, 0, 0),
			size=4,
			get=get_prop('uuid', rand_gen,'isuuidset'))
	export_tex = BoolProperty(
			name="Export Textures",
			description="Whether to export textures or not",
			default=False,
			options={'HIDDEN'})
	enable_advanced = BoolProperty(
			name="Adv. settings",
			description="Enables advanced settings",
			default=False,
			options={'HIDDEN', 'SKIP_SAVE'})

class MeshProps(PropertyGroup):
	version = EnumProperty(
			name="Version",
			description="Target version in Minecraft.",
			items=[("V1", "Version 1", "Version 1 of MHFC model files. Rev1_010814")],
			default="V1",
			options={'SKIP_SAVE'})
	armature = StringProperty(
			name="Armature",
			description="The armature that defines animations.",
			options=set())
	uv_layer = StringProperty(
			name="UV Layer",
			description="The uv layer for texture mappings",
			options=set())
	def update_name(self, context):
		result = prop = self.name
		if '/' in result:
			result = result.replace('/', '')
		if '\\' in result:
			result = result.replace('\\', '')
		if result != prop:
			self.name = result
	name = StringProperty(
			name="Model Name",
			description="The name of your model. ",
			update=update_name,
			subtype='FILE_NAME',
			options=set())
	poss_arms = CollectionProperty(
			type=ArmatureDESCR,
			name="Possible Armature",
			options={'SKIP_SAVE'})
	render_groups = CollectionProperty(
			type=RenderGroups,
			name="Render Groups")
	active_render_group = IntProperty(
			name="Active Render Group",
			default=-1)
	default_group_name = StringProperty(
			name="Default Group",
			description="The group all faces are in if not in a valid group",
			default="Default",
			options=set())
	default_img = StringProperty(
			name="Default Tex",
			description="The texture a face gets if it doesn't specify a specific one.",
			options=set())
	artist = StringProperty(
			name="Artist name",
			description="Your name",
			options=set())

class ActionProps(PropertyGroup):
	artist = StringProperty(
			name="Artist name",
			description="Your name",
			options=set())

class ObjectExporter(Operator):
	"""Exporter to export an object to the .mhmd file format"""
	bl_idname = "export_mesh.mhmd"
	bl_label = "Export MHMD"

	# Fileselector class uses this
	directory = StringProperty(
			name="Dir name",
			description="The folder to export to",
			subtype='DIR_PATH',
			options={'HIDDEN'})
	artist = StringProperty(
			name="Artist name",
			description="Your name",
			options=set())
	version = EnumProperty(
			name="Version",
			description="Target version in Minecraft.",
			items=[("V1", "Version 1", "Version 1 of MHFC model files. Rev1_010814")],
			default="V1")
	object = StringProperty(
			name="Object",
			description="The object to export",
			options=set())
	armature = StringProperty(
			name="Armature",
			description="The armature that defines animations.",
			options={'HIDDEN'})
	uv_layer = StringProperty(
			name="UV Layer",
			description="The uv layer for texture mappings",
			options={'HIDDEN'})
	default_group_name = StringProperty(
			name="Default Group",
			description="The group all faces are in if not in a valid group",
			default="Default",
			options=set())
	default_img = StringProperty(
			name="Default Tex",
			description="The texture a face gets if it doesn't specify a specific one.",
			options={'HIDDEN'})
	export_tex = BoolProperty(
			name="Export Textures",
			description="Whether to export textures or not",
			options={'HIDDEN'})
	mod_id = StringProperty(
			name="Mod ID",
			description="Your Mod ID",
			default="mhfc",
			options={'HIDDEN'})
	model_path = StringProperty(
			name="Path to the model",
			description="A formatstring to the path of you model. You may use {modid} and {modelname}.",
			default="{modid}:models/{modelname}/{modelname}.mhmd",
			options={'HIDDEN'})
	model_name = StringProperty(
			name="Model Name",
			description="The name of your model. ",
			options={'HIDDEN'})
	tex_path = StringProperty(
			name="Path to the textures",
			description="A formatstring to the textures. You may use {modid}, {modelname} and {texname}.",
			default="{modid}:textures/models/{modelname}/{texname}.png",
			options={'HIDDEN'})

	def execute(self, context):
		try:
			# set data from input
			warning.active_op = self
			# validate input
			# directory
			if not self.directory:
				error("Invalid directory")
			# artist
			# version
			# object
			if not self.object:
				self.object = context.object.name
			obj = extract_safe(bpy.data.objects, self.object, "Object {obj} not in data!", obj=self.object)
			if obj.type != 'MESH':
				error("Active Object not a Mesh")
			# armature
			try:
				arm = bpy.data.objects[self.armature]
			except KeyError:
				if self.armature:
					error("Armature {arm} not in data", arm=self.armature)
			else:
				if arm.type != "ARMATURE":
					error("'armature' {arm} is not an armature", arm=op.armature)
				elif arm not in [mod.object for mod in obj.modifiers if mod.type == 'ARMATURE']:
					error("Armature {arm} is not active on object {obj}", arm=op.armature, obj=op.object)
			# uv layer
			if not self.uv_layer:
				self.uv_layer = obj.data.uv_layers.active.name
			extract_safe(obj.data.uv_layers, self.uv_layer, "Invalid UV-Layer {uv}", uv=self.uv_layer)
			# default_group_name
			if self.default_group_name in obj.data.mhfcprops.render_groups:
				error("Default groupname can't duplicate group name")
			# default_img
			extract_safe(bpy.data.images, self.default_img, "Invalid default image {img}", img=self.default_img)
			# mod_id
			if not self.mod_id:
				self.mod_id = 'mhfc'
			# model_path
			if not self.model_path:
				self.model_path = '{modid}:models/{modelname}/{modelname}.mhmd'
			# model name
			self.model_name = bpy.path.ensure_ext(self.model_name, '.mhmd')[:-5]
			# texture_path
			if not self.tex_path:
				self.tex_path = '{modid}:textures/models/{modelname}/{texname}.png'
			# export
			export_selected(context, self)
		except FatalException as ex:
			self.report({'ERROR'}, "That should not have happened\n"+ex.value)
		except ErrorException as ex:
			self.report({'ERROR_INVALID_INPUT'}, "Check your input!\n"+ex.value)
		else:
			return {'FINISHED'}
		return {'CANCELLED'}

class ArmAnimationExporter(Operator, ExportHelper):
	"""Exports the active action of the selected armature"""
	bl_idname = "export_anim.mhanm"
	bl_label = "Export MHANM"
	
	filename_ext = ".mhanm"
	
	filter_glob = StringProperty(
			default="*.mhanm",
			options={'HIDDEN'},
			)
	
	arm_action = StringProperty(
			name="Action",
			description="The action to export",
			)
	
	artist = StringProperty(
			name="Artist",
			description="The artist of this action",
			default=""
			)
	
	start_frame = IntProperty(
			name="Animation begin",
			description="The frame the animation should begin at",
			)
	
	end_frame = IntProperty(
			name="Animation end",
			description="The frame the animation should end at",
			)
	
	@staticmethod
	def export(filepath, action, start, end, artist):
		class Animation(object):
			def __init__(self, fcurve, start, end):
				# self.intrapolation_mode
				if fcurve is not None:
					self.points = [keyf for keyf in fcurve.keyframe_points if start <= keyf.co[0] <= end]
					self.extrapolation_mode = fcurve.extrapolation
				else:
					self.points = []
					#self.extrapolation_mode = 0
			def to_file(self, file_h):
				interpolations = {'CONSTANT': 8, 'LINEAR': 9, 'BEZIER': 10}
				extrapolations = {'CONSTANT': 16, 'LINEAR': 17}
				points = self.points
				def write_point(co):
					write_packed(">2f", file_h, co[0] - start, co[1])
				anim_len = len(points)
				if anim_len > 2**16 - 1:
					error("Too many keyframes in fcurve to export")
				# animation length
				write_packed(">H", file_h, anim_len)
				if anim_len == 0:
					return
				write_point(points[0].co)
				# TUNE IN
				write_packed(">B", file_h, 0) # = LINEAR
				# POINTS
				for left, right in zip(points[:], points[1:]):
					write_point(right.co)
					raw_mode = right.interpolation
					try:
						write_packed(">B", file_h, interpolations[raw_mode])
						if raw_mode == 'BEZIER':
							write_point(left.handle_right)
							write_point(right.handle_left)
					except KeyError:
						warning("[MHAnmExporter] Unknown interpolation mode {mode}", mode=raw_mode)
				# TUNE OUT
				try:
					write_packed(">B", file_h, extrapolations[self.extrapolation_mode])
					if self.extrapolation_mode == 'LINEAR':
						write_point(points[-1].handle_right)
				except KeyError:
					warning("[MHAnmExporter] Unknown extrapolation mode {mode}", mode=self.extrapolation_mode)
		
		class BoneAction(object):
			
			def __init__(self, name, curves, start, end):
				loc_curves, rot_curves, scale_curves = curves
				self.name = name
				self.loc_x = Animation(loc_curves[0], start, end)
				self.loc_y = Animation(loc_curves[1], start, end)
				self.loc_z = Animation(loc_curves[2], start, end)
				self.rot_w = Animation(rot_curves[0], start, end)
				self.rot_x = Animation(rot_curves[1], start, end)
				self.rot_y = Animation(rot_curves[2], start, end)
				self.rot_z = Animation(rot_curves[3], start, end)
				self.scale_x = Animation(scale_curves[0], start, end)
				self.scale_y = Animation(scale_curves[1], start, end)
				self.scale_z = Animation(scale_curves[2], start, end)
			
			def to_file(self, file_h):
				write_string(self.name, file_h)
				self.loc_x.to_file(file_h)
				self.loc_y.to_file(file_h)
				self.loc_z.to_file(file_h)
				self.rot_x.to_file(file_h)
				self.rot_y.to_file(file_h)
				self.rot_z.to_file(file_h)
				self.rot_w.to_file(file_h)
				self.scale_x.to_file(file_h)
				self.scale_y.to_file(file_h)
				self.scale_z.to_file(file_h)
		
		bone_data_re = re.compile("pose\.bones\[\"(.*)\"\].(rotation_quaternion|location|scale)")
		bone_dict = defaultdict(lambda: ([None]*3, [None]*4, [None]*3))
		for curve in action.fcurves:
			match = bone_data_re.match(curve.data_path)
			if match is None:
				continue
			bone, key = match.groups()
			if key == 'location':
				bone_dict[bone][0][curve.array_index] = curve
			elif key == 'rotation_quaternion':
				bone_dict[bone][1][curve.array_index] = curve
			elif key == 'scale':
				bone_dict[bone][2][curve.array_index] = curve
		bone_count = len(bone_dict)
		if bone_count > 255:
			error("Too many bones to export")
		with open(filepath, 'wb') as file_h:
			file_h.write(b'MHFC ANM')
			write_string(artist, file_h)
			write_packed(">B", file_h, bone_count)
			for bone in bone_dict:
				BoneAction(bone, bone_dict[bone], start, end).to_file(file_h)
		return {'FINISHED'}
	
	@staticmethod
	def guess_action(context):
		obj = context.object
		if obj.type != 'ARMATURE':
			raise ValueError("Active object no armature")
		anim_data = obj.animation_data
		if anim_data is None:
			raise ValueError("Active object has no animation data")
		action = anim_data.action
		if action is None:
			raise ValueError("Active object has no action bound")
		return action.name
	
	def execute(self, context):
		if not self.arm_action:
			try:
				self.arm_action = ArmAnimationExporter.guess_action(context)
			except ValueError as ex:
				error("No action to export given. Guessing from active armature failed: {err}", err=ex.value)
		action = extract_safe(bpy.data.actions, self.arm_action, "Invalid action: {act}", act=self.arm_action)
		return ArmAnimationExporter.export(self.filepath, action, self.start_frame, self.end_frame, self.artist)

class ArmatureUpdater(Operator):
	bl_idname = "object.mhfc_update_arms"
	bl_label = "Update possible armatures"

	@classmethod
	def poll(cls, context):
		return context.object is not None and context.object.type == 'MESH'

	def execute(self, context):
		obj = context.object
		armlist = obj.data.mhfcprops.poss_arms
		armlist.clear()
		for mod in obj.modifiers:
			if mod.type == 'ARMATURE' and mod.object is not None:
				item = armlist.add()
				item.name = mod.object.name
		return {'FINISHED'}

class AddRenderGroup(Operator):
	"""Adds a group of points."""
	bl_idname = "object.mhfc_group_add"
	bl_label = "Add render group"

	@classmethod
	def poll(cls, context):
		return context.object is not None and context.object.type == 'MESH'

	def execute(self, context):
		data = context.object.data
		props = data.mhfcprops
		groups = props.render_groups
		props.active_render_group = len(groups)
		g = groups.add()
		g.ident = "Default"
		return {'FINISHED'}

class RemoveRenderGroup(Operator):
	"""Removes the active render group."""
	bl_idname = "object.mhfc_group_remove"
	bl_label = "Add render group"

	@classmethod
	def poll(cls, context):
		return context.object is not None and context.object.type == 'MESH' and context.object.data.mhfcprops.active_render_group >= 0

	def execute(self, context):
		data = context.object.data
		props = data.mhfcprops
		groups = props.render_groups
		groups.remove(props.active_render_group)
		props.active_render_group -= 1 if len(groups) > 0 else 0
		return {'FINISHED'}

class AddFacesToGroup(Operator):
	"""Adds all selected faces to the current render group. They can't be removed though: Ultimately no face should be without group"""
	bl_idname = "object.mhfc_group_add_faces"
	bl_label = "Assign"

	@classmethod
	def poll(cls, context):
		if context.mode != 'EDIT_MESH' or context.object is None or context.object.type != 'MESH':
			return False
		props = context.object.data.mhfcprops
		curr = props.active_render_group
		return curr >= 0 and curr < len(props.render_groups)

	def execute(self, context):
		data = context.object.data
		active_group = data.mhfcprops.active_render_group
		bm = bmesh.from_edit_mesh(data)
		if 'MHFCRenderGroupIndex' not in bm.faces.layers.int:
			bm.faces.layers.int.new('MHFCRenderGroupIndex')
		g_layer = bm.faces.layers.int['MHFCRenderGroupIndex']
		for face in bm.faces:
			if not face.select:
				continue
			face[g_layer] = active_group + 1
		bmesh.update_edit_mesh(data)
		return {'FINISHED'}

class SelectGroup(Operator):
	"""Selects all faces which belong to the current group"""
	bl_idname = "object.mhfc_group_select_faces"
	bl_label = "Select"

	@classmethod
	def poll(cls, context):
		if context.mode != 'EDIT_MESH' or context.object is None or context.object.type != 'MESH':
			return False
		props = context.object.data.mhfcprops
		curr = props.active_render_group
		return curr >= 0 and curr < len(props.render_groups) and 'MHFCRenderGroupIndex' in context.object.data.polygon_layers_int

	def execute(self, context):
		data = context.object.data
		active_group = data.mhfcprops.active_render_group
		bm = bmesh.from_edit_mesh(data)
		g_layer = bm.faces.layers.int['MHFCRenderGroupIndex']
		for face in bm.faces:
			face.select = (face[g_layer] == active_group + 1)
		bmesh.update_edit_mesh(data)
		return {'FINISHED'}

class UpdateGroupsVisual(Operator):
	"""Updates the Blender-internal texture assignement"""
	bl_idname = "object.mhfc_group_update_tex"
	bl_label = "Update Textures"

	mode = EnumProperty(
			items=[	("ALL", "All Faces", "Updates all faces on the mesh"),
					("SELECTED", "Selected Faces", "Updates only selected faces")],
			name="Mode",
			default="ALL")

	@classmethod
	def poll(cls, context):
		if context.mode != "EDIT_MESH":
			return False
		obj = context.object
		if obj is None or obj.type != "MESH":
			return False
		data = obj.data
		return obj.data.uv_layers.active is not None and 'MHFCRenderGroupIndex' in data.polygon_layers_int

	def execute(self, context):
		data = context.object.data
		bm = bmesh.from_edit_mesh(data)
		groups = data.mhfcprops.render_groups
		g_lyr = bm.faces.layers.int['MHFCRenderGroupIndex']
		tex_lyr = bm.faces.layers.tex.active
		for face in bm.faces:
			if self.mode == "SELECTED" and not face.select:
				continue
			idx = face[g_lyr] - 1
			if idx < 0:
				continue
			if idx < len(groups) and groups[idx].image in bpy.data.images:
				print(groups[idx].image)
				face[tex_lyr].image = bpy.data.images[groups[idx].image]
			else:
				face[g_lyr] = 0
		bmesh.update_edit_mesh(data)
		context.scene.render.engine = "BLENDER_RENDER"
		return {'FINISHED'}

class RenderGroupUIList(UIList):
	def draw_item(self, context, layout, data, item, icon, active_data, active_propname):
		if self.layout_type in {'DEFAULT', 'COMPACT'}:
			if item:
				if item.image in bpy.data.images:
					layout.prop(item, "ident", text="", emboss=False, icon_value=icon)
				else:
					row = layout.row()
					row.prop(item, "ident", text="", emboss=False, icon_value=icon)
					row.label(icon="ERROR")
			else:
				layout.label(text="", translate=False, icon_value=icon)
		# 'GRID' layout type should be as compact as possible (typically a single icon!).
		elif self.layout_type in {'GRID'}:
			layout.alignment = 'CENTER'
			layout.label(text="", icon_value=icon)

class AnimationExportMenu(Menu):
	bl_label = "Export Action"
	bl_idname = "animation.mhfc_export"

	def draw(self, context):
		layout = self.layout
		sce = context.scene
		if context.space_data.mode == 'ACTION':
			row = layout.row()
			op = row.operator(ArmAnimationExporter.bl_idname)
			if not context.space_data.action:
				row.enabled = False
			else:
				op.arm_action = context.space_data.action.name
			op.start_frame = sce.frame_start
			op.end_frame = sce.frame_end

class AnimationExportHeader(Header):
	bl_label = "Custom Menu"
	bl_idname = "animation.mhfc_export"
	bl_space_type = "DOPESHEET_EDITOR"

	def draw(self, context):
		layout = self.layout
		layout.menu(AnimationExportMenu.bl_idname)

class ObjectExportPanel(Panel):
	# MHFC Panel under Object
	bl_label = "Monster Hunter Frontier Craft"
	bl_idname = "object.mhfc_export"
	bl_region_type = "WINDOW"
	bl_space_type = "PROPERTIES"
	bl_context = "object"

	@classmethod
	def poll(cls, context):
		return context.object is not None and context.object.type == 'MESH'

	def draw(self, context):
		# setup vars
		obj = context.object
		data = obj.data
		meshprops = data.mhfcprops
		sceneprops = context.scene.mhfcprops
		prefs = context.user_preferences.addons[__name__].preferences
		layout = self.layout
		error = False
		# check for invalidity
		if obj.type != 'MESH':
			layout.label("Please select a Mesh to export")
			return
		# Add advanced toggle
		row = layout.row()
		row.alignment = 'RIGHT'
		row.prop(sceneprops, 'enable_advanced')
		# General properties
		box = layout.box()
		box.label(text="General properties")
		box.prop(prefs, 'directory', text="Resource directory")
		if not prefs.directory:
			error = True
			box.label(text="Select resource path", icon="ERROR")
			box.separator()
		box.prop(prefs, 'mod_id', text="Mod ID")
		# Properties of this object
		box = layout.box()
		box.label(text="Object properties")
		box.prop(meshprops, 'artist', text="Artist name")
		box.prop(meshprops, 'name', text="Model Name")
		if not meshprops.name:
			error = True
			box.label(text="Select model name", icon="ERROR")
			box.separator()
		box.prop_search(meshprops, 'armature', meshprops, 'poss_arms', text="Armature", icon="ARMATURE_DATA")
		if meshprops.armature and meshprops.armature not in meshprops.poss_arms:
			error = True
			box.label(text="Invalid Armature", icon="ERROR")
			box.separator()
		box.operator(ArmatureUpdater.bl_idname)
		box.prop_search(meshprops, 'uv_layer', data, 'uv_layers', text="UV Layer", icon="GROUP_UVS")
		if not meshprops.uv_layer:
			error = True
			box.label(text="Select a UV map.", icon="ERROR")
			box.separator()
		elif meshprops.uv_layer not in data.uv_layers:
			error = True
			box.label(text="Invalid UV Map", icon="ERROR")
			box.separator()
		box = box.box()
		box.label(text="Default group")
		box.prop(meshprops, 'default_group_name', text="Name")
		if not meshprops.default_group_name:
			error = True
			box.label(text="Select a default group name.", icon="ERROR")
			layout.separator()
		elif meshprops.default_group_name in [g.ident for g in meshprops.render_groups]:
			error = True
			box.label(text="Name collision with existing group.", icon="ERROR")
			layout.separator()
		box.prop_search(meshprops, 'default_img', bpy.data, 'images', text="Texture", icon="IMAGE_DATA")
		if not meshprops.default_img:
			error = True
			box.label(text="Select default texture.", icon="ERROR")
			layout.separator()
		elif meshprops.default_img not in bpy.data.images:
			error = True
			layout.label(text="Invalid image texture", icon="ERROR")
			layout.separator()
		if sceneprops.enable_advanced:
			layout.prop(meshprops, 'version', text="Version")
			layout.prop(prefs, 'tex_path', text="Tex Path")
			layout.prop(prefs, 'model_path', text="Model Path")
		layout.prop(sceneprops, 'export_tex', text="Export Textures")
		# Add operator
		opertor_box = layout.row()
		op = opertor_box.operator(ObjectExporter.bl_idname)
		op.object = obj.name
		op.version = meshprops.version
		op.armature = meshprops.armature
		op.uv_layer = meshprops.uv_layer
		op.default_group_name = meshprops.default_group_name
		op.default_img = meshprops.default_img
		op.model_name = meshprops.name
		op.artist = meshprops.artist
		op.export_tex = sceneprops.export_tex
		op.mod_id = prefs.mod_id
		op.tex_path = prefs.tex_path
		op.model_path = prefs.model_path
		op.directory = prefs.directory
		if error:
			opertor_box.enabled = False

class MeshDataPanel(Panel):
	# MHFC Panel under Object
	bl_idname = "object.mhfc_meshdata"
	bl_label = "MHFC Render Groups"
	bl_region_type = "WINDOW"
	bl_space_type = "PROPERTIES"
	bl_context = "data"

	@classmethod
	def poll(cls, context):
		return context.object is not None and context.object.type == 'MESH'

	def draw(self, context):
		layout = self.layout
		props = context.object.data.mhfcprops
		groups = props.render_groups
		active_idx = props.active_render_group
		
		row = layout.row()
		row.template_list(
				listtype_name='RenderGroupUIList',
				dataptr=props,
				propname='render_groups',
				active_dataptr=props,
				active_propname='active_render_group',
				rows=2,
				type='DEFAULT',
				)
		col = row.column(align=True)
		col.operator(
				AddRenderGroup.bl_idname,
				text="", icon='ZOOMIN')
		col.operator(
				RemoveRenderGroup.bl_idname,
				text="", icon='ZOOMOUT')
		layout.label("Count %d" % len(props.render_groups))
		if active_idx >= 0 and active_idx < len(groups):
			active_g = groups[active_idx]
			layout.prop_search(active_g, 'image', bpy.data, 'images', text="Image", icon="IMAGE_DATA")
			if not active_g.image:
				layout.label(text="Select an imagetexture for active group", icon="ERROR")
			elif not active_g.image in bpy.data.images:
				layout.label(text="Invalid image texture", icon="ERROR")
		if context.mode == 'EDIT_MESH':
			row = layout.row()
			row.operator(AddFacesToGroup.bl_idname)
			row.operator(SelectGroup.bl_idname)
			layout.operator_menu_enum(UpdateGroupsVisual.bl_idname, 'mode')

class AnimationExportPanel(Panel):
	# MHFC Panel under Object
	bl_space_type = "VIEW_3D"
	bl_region_type = "TOOLS"
	bl_category = "Export"
	bl_label = "Monster Hunter Frontier Craft"
	bl_idname = "view3d.mhanm_export"

	@classmethod
	def poll(cls, context):
		return context.object is not None and context.object.type == 'ARMATURE'

	def draw(self, context):
		layout = self.layout
		obj = context.object
		sce = context.scene
		row = layout.row()
		op = row.operator(ArmAnimationExporter.bl_idname)
		if obj.animation_data is not None and obj.animation_data.action is not None:
			action = obj.animation_data.action
			op.arm_action = action.name
			layout.prop(action.mhfcprops, 'artist')
			op.artist = action.mhfcprops.artist
		else:
			row.enabled = False
		op.start_frame = sce.frame_start
		op.end_frame = sce.frame_end

@persistent
def load_handler(_):
	# reset if loaded file is the default file
	if bpy.data.filepath == '':
		for scene in bpy.data.scenes:
			scene.mhfcprops.isuuidset = False

def register():
	# properties
	bpy.utils.register_class(ArmatureDESCR)
	bpy.utils.register_class(RenderGroups)
	bpy.utils.register_class(SceneProps)
	bpy.utils.register_class(MeshProps)
	bpy.utils.register_class(ActionProps)
	# operators
	bpy.utils.register_class(ObjectExporter)
	bpy.utils.register_class(ArmatureUpdater)
	bpy.utils.register_class(AddRenderGroup)
	bpy.utils.register_class(RemoveRenderGroup)
	bpy.utils.register_class(AddFacesToGroup)
	bpy.utils.register_class(SelectGroup)
	bpy.utils.register_class(UpdateGroupsVisual)
	bpy.utils.register_class(ArmAnimationExporter)
	# UIList
	bpy.utils.register_class(RenderGroupUIList)
	bpy.utils.register_class(AnimationExportMenu)
	# headers
	bpy.utils.register_class(AnimationExportHeader)
	# panels
	bpy.utils.register_class(ObjectExportPanel)
	bpy.utils.register_class(MeshDataPanel)
	bpy.utils.register_class(AnimationExportPanel)
	# addon prefs
	bpy.utils.register_class(Preferences)
	bpy.app.handlers.load_post.append(load_handler)
	# scene props
	Scene.mhfcprops = PointerProperty(type=SceneProps)
	# meshprops
	Mesh.mhfcprops = PointerProperty(type=MeshProps)
	# actionprops
	Action.mhfcprops = PointerProperty(type=ActionProps)

def unregister():
	# props
	bpy.utils.unregister_class(ArmatureDESCR)
	bpy.utils.unregister_class(RenderGroups)
	bpy.utils.unregister_class(SceneProps)
	bpy.utils.unregister_class(MeshProps)
	bpy.utils.unregister_class(ActionProps)
	# operators
	bpy.utils.unregister_class(ObjectExporter)
	bpy.utils.unregister_class(ArmatureUpdater)
	bpy.utils.unregister_class(AddRenderGroup)
	bpy.utils.unregister_class(RemoveRenderGroup)
	bpy.utils.unregister_class(AddFacesToGroup)
	bpy.utils.unregister_class(SelectGroup)
	bpy.utils.unregister_class(UpdateGroupsVisual)
	bpy.utils.unregister_class(ArmAnimationExporter)
	# UIList
	bpy.utils.unregister_class(RenderGroupUIList)
	bpy.utils.unregister_class(AnimationExportMenu)
	# header
	bpy.utils.unregister_class(AnimationExportHeader)
	# panels
	bpy.utils.unregister_class(ObjectExportPanel)
	bpy.utils.unregister_class(MeshDataPanel)
	bpy.utils.unregister_class(AnimationExportPanel)
	# addon prefs
	bpy.utils.unregister_class(Preferences)
	# scene props
	del Scene.mhfcprops
	# meshprops
	del Mesh.mhfcprops
	# actionprops
	del Action.mhfcprops

if __name__ == "__main__":
	register()
