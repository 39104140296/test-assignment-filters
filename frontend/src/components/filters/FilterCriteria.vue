<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import { useFilterStore } from '@/stores/filterStore'

const props = defineProps({
  criteria: Object,
  showDeleteButton: Boolean
})

const emit = defineEmits(['update:criteria', 'delete:criteria'])

const filterStore = useFilterStore()

const localCriteria = ref({ ...props.criteria })
const isInitialLoad = ref(true)

const criteriaTypes = computed(() => filterStore.criteriaTypes)

const filteredConditions = computed(() => {
  return filterStore.comparisonConditions.filter((condition) => {
    return condition.criteriaType.criteriaTypeId === localCriteria.value.criteriaType.criteriaTypeId
  })
})

const onCriteriaTypeChange = () => {
  if (filteredConditions.value.length > 0) {
    localCriteria.value.comparisonCondition.conditionId = filteredConditions.value[0].conditionId
  } else {
    localCriteria.value.comparisonCondition.conditionId = null
  }

  if (!isInitialLoad.value) {
    localCriteria.value.criteriaValue = ''
  }

  emit('update:criteria', { ...localCriteria.value })
}

const removeCriteria = () => {
  emit('delete:criteria', props.criteria.criteriaId)
}

onMounted(() => {
  isInitialLoad.value = false
})

watch(
  localCriteria,
  (newVal) => {
    emit('update:criteria', newVal)
  },
  { deep: true }
)
</script>

<template>
  <div class="criteria-row">
    <select v-model="localCriteria.criteriaType.criteriaTypeId" @change="onCriteriaTypeChange">
      <option v-for="type in criteriaTypes" :key="type.criteriaTypeId" :value="type.criteriaTypeId">
        {{ type.typeName }}
      </option>
    </select>

    <select v-model="localCriteria.comparisonCondition.conditionId">
      <option
        v-for="condition in filteredConditions"
        :key="condition.conditionId"
        :value="condition.conditionId"
      >
        {{ condition.conditionName }}
      </option>
    </select>

    <input v-model="localCriteria.criteriaValue" />
    <button class="delete-btn" v-if="showDeleteButton" @click="removeCriteria">DELETE</button>
  </div>
</template>

<style scoped>
.criteria-row {
  display: flex;
  gap: 8px;
  padding: 8px;
  align-items: center;
}

.criteria-row select,
.criteria-row input {
  padding: 4px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.delete-btn {
  padding: 2px 6px;
  border: 1px solid #f5f5f5;
  background-color: #f5f5f5;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
}

.delete-btn:hover {
  background-color: #e2e2ff;
}
</style>
