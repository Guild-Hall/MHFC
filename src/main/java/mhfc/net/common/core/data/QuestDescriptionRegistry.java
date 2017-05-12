package mhfc.net.common.core.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mhfc.net.common.quests.api.GoalDefinitionDelegate;
import mhfc.net.common.quests.api.IGoalDefinition;
import mhfc.net.common.quests.api.IQuestDefinition;
import mhfc.net.common.quests.api.QuestDefinitionDelegate;

public class QuestDescriptionRegistry {

	public static interface IQuestDescriptionDirector {
		public void construct(QuestDescriptionRegistry data);
	}

	public static class QuestGroupData {
		private final Map<String, Set<String>> groupMapping = new HashMap<>();
		private final LinkedHashSet<String> groupIDs = new LinkedHashSet<>();

		public Map<String, Set<String>> getGroupMapping() {
			return groupMapping;
		}

		public LinkedHashSet<String> getGroupIDs() {
			return groupIDs;
		}

		private void ensureGroupID(String groupID) {
			if (groupIDs.contains(groupID)) {
				return;
			}
			groupIDs.add(groupID);
			groupMapping.put(groupID, new HashSet<String>());
		}

		public void addGroupID(String id) {
			ensureGroupID(id);
		}

		public void addQuestToGroup(String groupID, String questID) {
			ensureGroupID(groupID);
			groupMapping.get(groupID).add(questID);
		}

		public void addQuestsToGroup(String groupID, Collection<String> quests) {
			ensureGroupID(groupID);
			groupMapping.get(groupID).addAll(quests);
		}

		/**
		 * Merges the data parameter into this data. The parameter overrides previous data held.
		 */
		public void addInto(QuestGroupData data) {
			groupMapping.putAll(data.groupMapping);
			groupIDs.addAll(data.groupIDs);
		}

		public void clear() {
			groupMapping.clear();
			groupIDs.clear();
		}

		/**
		 * Orders the group identifiers as they are ordered in the iterable. If a group doesn't exist already, it is
		 * created empty.
		 */
		public void orderGroups(Iterable<String> groupIDsInOrder) {
			LinkedHashSet<String> oldOrder = new LinkedHashSet<>(groupIDs);
			Map<String, Set<String>> oldMapping = new HashMap<>(groupMapping);
			groupMapping.clear();
			groupIDs.clear();
			for (String groupID : groupIDsInOrder) {
				if (!oldOrder.contains(groupID)) {
					continue;
				}
				assert oldMapping.containsKey(groupID);
				addQuestsToGroup(groupID, oldMapping.get(groupID));
			}
		}
	}

	private final HashMap<String, QuestDefinitionDelegate> questDescriptions = new HashMap<>();
	private final HashMap<String, GoalDefinitionDelegate> goalDescriptions = new HashMap<>();
	private final QuestGroupData groupData = new QuestGroupData();

	public void fillQuestDescriptions(Map<String, QuestDefinitionDelegate> mapData) {
		questDescriptions.putAll(mapData);
	}

	public void putQuestDescription(String identifier, QuestDefinitionDelegate questDescription) {
		questDescriptions.put(identifier, questDescription);
	}

	public void fillGoalDescriptions(Map<String, GoalDefinitionDelegate> mapData) {
		goalDescriptions.putAll(mapData);
	}

	public void putGoalDescription(String identifier, GoalDefinitionDelegate questDescription) {
		goalDescriptions.put(identifier, questDescription);
	}

	public void addGroups(QuestGroupData data) {
		groupData.addInto(data);
	}

	public void addGroup(String groupID, Collection<String> quests) {
		groupData.addQuestsToGroup(groupID, quests);
	}

	public IQuestDefinition getQuestDescription(String id) {
		QuestDefinitionDelegate qd = questDescriptions.get(id);
		return qd.getValue();
	}

	public IGoalDefinition getGoalDescription(String id) {
		GoalDefinitionDelegate qd = goalDescriptions.get(id);
		return qd.getValue();
	}

	public Map<String, QuestDefinitionDelegate> getFullQuestDescriptionMap() {
		return Collections.unmodifiableMap(questDescriptions);
	}

	public Map<String, GoalDefinitionDelegate> getFullGoalDescriptionMap() {
		return Collections.unmodifiableMap(goalDescriptions);
	}

	/**
	 * <b>WARNING:</b> The objected returned here is backed by the real map
	 */
	public Map<String, Set<String>> getFullGroupMap() {
		return Collections.unmodifiableMap(groupData.groupMapping);
	}

	public List<String> getGroupsInOrder() {
		return new ArrayList<>(groupData.groupIDs);
	}

	public Set<String> getQuestIdentifiersFor(String group) {
		Set<String> identifiers = groupData.groupMapping.get(group);
		if (identifiers == null) {
			return Collections.emptySet();
		}
		return new HashSet<>(identifiers);
	}

	public void clearData() {
		questDescriptions.clear();
		goalDescriptions.clear();
		groupData.clear();
	}

}
