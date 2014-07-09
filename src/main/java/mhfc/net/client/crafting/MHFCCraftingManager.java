package mhfc.net.client.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mhfc.net.client.crafting.recipes.MHFCShapedRecipes;
import mhfc.net.client.crafting.recipes.MHFCShapelessRecipes;
import mhfc.net.common.core.registry.MHFCRegBlock;
import mhfc.net.common.core.registry.MHFCRegCraftingHunter;
import mhfc.net.common.core.registry.MHFCRegItem;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class MHFCCraftingManager {
	
	private static final MHFCCraftingManager instance = new MHFCCraftingManager();
	private static MHFCRegItem x;
	private static MHFCRegBlock y;
	private List recipes = new ArrayList();

	public static final MHFCCraftingManager getInstance()
	{
	return instance;
	}

	public MHFCCraftingManager()
	{	
	
	// Items	
	MHFCRegCraftingHunter.craftAll();
    addShapedRecipe(new ItemStack(x.MHFCItemHTigrex, 1), new Object[]{"XXX","XTX","TXT"," S "," S ",'X',x.MHFCItemTigrexShell,'T',x.MHFCItemTigrexScale,'S',Items.stick});
	addShapedRecipe(new ItemStack(x.mhfcitemgsbone, 1), new Object[]{" X ", "TXT","TXT"," S "," S ",'X', Items.iron_ingot,'T',Items.bone,'S', Items.stick});
	addShapedRecipe(new ItemStack(x.MHFCItemTrapTool, 1), new Object[]{"   ","   ","XXX","XXA","AXA", 'X', x.MHFCItemBombMaterial,'A', Items.gunpowder});
	
	//	Blocks
	addShapedRecipe(new ItemStack(y.mhfcblockstuntrap, 1), new Object[]{"ADX","DXF","XXA","FXD","XXX",'X', x.MHFCItemTrapTool,'A', Items.redstone, 'D', Items.iron_ingot, 'F', x.MHFCItemBombMaterial});
	}
	
	public ItemStack findMatchingRecipe(InventoryCrafting par1InventoryCrafting, World par2World)
	{
	int var3 = 0;
	ItemStack var4 = null;
	ItemStack var5 = null;
	int var6;

	for (var6 = 0; var6 < par1InventoryCrafting.getSizeInventory(); ++var6)
	{
	ItemStack var7 = par1InventoryCrafting.getStackInSlot(var6);

	if (var7 != null)
	{
	if (var3 == 0)
	{
	var4 = var7;
	}

	if (var3 == 1)
	{
	var5 = var7;
	}

	++var3;
	}
	}

	if (var3 == 2 && var4.getItem() == var5.getItem() && var4.stackSize == 1 && var5.stackSize == 1 && var5.getItem().isRepairable())
	{
	Item var11 = var5.getItem();
	int var13 = var11.getMaxDamage() - var4.getItemDamageForDisplay();
	int var8 = var11.getMaxDamage() - var5.getItemDamageForDisplay();
	int var9 = var13 + var8 + var11.getMaxDamage() * 5 / 100;
	int var10 = var11.getMaxDamage() - var9;

	if (var10 < 0)
	{
	var10 = 0;
	}

	return new ItemStack(var4.getItem(), 1, var10);
	}
	else
	{
	for (var6 = 0; var6 < this.recipes.size(); ++var6)
	{
	IRecipe var12 = (IRecipe)this.recipes.get(var6);

	if (var12.matches(par1InventoryCrafting, par2World))
	{
	return var12.getCraftingResult(par1InventoryCrafting);
	}
	}

	return null;
	}
	}

	public List getRecipeList()
	{
	return this.recipes;
	}
	
	public MHFCShapedRecipes addShapedRecipe(ItemStack par1ItemStack, Object ... par2ArrayOfObj)
	{
	String var3 = "";
	int var4 = 0;
	int var5 = 0;
	int var6 = 0;

	if (par2ArrayOfObj[var4] instanceof String[])
	{
	String[] var7 = (String[])((String[])par2ArrayOfObj[var4++]);

	for (int var8 = 0; var8 < var7.length; ++var8)
	{
	String var9 = var7[var8];
	++var6;
	var5 = var9.length();
	var3 = var3 + var9;
	}
	}
	else
	{
	while (par2ArrayOfObj[var4] instanceof String)
	{
	String var11 = (String)par2ArrayOfObj[var4++];
	++var6;
	var5 = var11.length();
	var3 = var3 + var11;
	}
	}

	HashMap var12;

	for (var12 = new HashMap(); var4 < par2ArrayOfObj.length; var4 += 2)
	{
	Character var13 = (Character)par2ArrayOfObj[var4];
	ItemStack var14 = null;

	if (par2ArrayOfObj[var4 + 1] instanceof Item)
	{
	var14 = new ItemStack((Item)par2ArrayOfObj[var4 + 1]);
	}
	else if (par2ArrayOfObj[var4 + 1] instanceof Block)
	{
	var14 = new ItemStack((Block)par2ArrayOfObj[var4 + 1], 1, -1);
	}
	else if (par2ArrayOfObj[var4 + 1] instanceof ItemStack)
	{
	var14 = (ItemStack)par2ArrayOfObj[var4 + 1];
	}

	var12.put(var13, var14);
	}

	ItemStack[] var15 = new ItemStack[var5 * var6];

	for (int var16 = 0; var16 < var5 * var6; ++var16)
	{
	char var10 = var3.charAt(var16);

	if (var12.containsKey(Character.valueOf(var10)))
	{
	var15[var16] = ((ItemStack)var12.get(Character.valueOf(var10))).copy();
	}
	else
	{
	var15[var16] = null;
	}
	}

	MHFCShapedRecipes var17 = new MHFCShapedRecipes(var5, var6, var15, par1ItemStack);
	this.recipes.add(var17);
	return var17;
	}

	public void addShapelessRecipe(ItemStack par1ItemStack, Object ... par2ArrayOfObj)
	{
	ArrayList var3 = new ArrayList();
	Object[] var4 = par2ArrayOfObj;
	int var5 = par2ArrayOfObj.length;

	for (int var6 = 0; var6 < var5; ++var6)
	{
	Object var7 = var4[var6];

	if (var7 instanceof ItemStack)
	{
	var3.add(((ItemStack)var7).copy());
	}
	else if (var7 instanceof Item)
	{
	var3.add(new ItemStack((Item)var7));
	}
	else
	{
	if (!(var7 instanceof Block))
	{
	throw new RuntimeException("Invalid shapeless recipe!");
	}

	var3.add(new ItemStack((Block)var7));
	}
	}

	this.recipes.add(new MHFCShapelessRecipes(par1ItemStack, var3));
	}



}
