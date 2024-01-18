<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import { useFilterStore } from '@/store/filterStore'
import { format, getDaysInMonth, parseISO } from 'date-fns'

const props = defineProps({
  criteria: Object,
  showDeleteButton: Boolean
})

const emit = defineEmits(['update:criteria', 'delete:criteria'])

const filterStore = useFilterStore()

const localCriteria = ref({
  ...props.criteria,
  criteriaType: { ...props.criteria.criteriaType },
  comparisonCondition: { ...props.criteria.comparisonCondition }
})

const isInitialLoad = ref(true)

const isDateType = computed(
  () => localCriteria.value.criteriaType.criteriaTypeId === dateTypeId.value
)

const inputType = computed(() => {
  if (localCriteria.value.criteriaType.criteriaTypeId === amountTypeId.value) {
    return 'number'
  }
  return 'text'
})

const amountTypeId = computed(() => {
  const amountType = filterStore.filterCriteriaOptions.criteriaTypes.find(
    (type) => type.typeName === 'Amount'
  )
  return amountType ? amountType.criteriaTypeId : null
})

const dateTypeId = computed(() => {
  const dateType = filterStore.filterCriteriaOptions.criteriaTypes.find(
    (type) => type.typeName === 'Date'
  )
  return dateType ? dateType.criteriaTypeId : null
})

const filteredConditions = computed(() => {
  return filterStore.filterCriteriaOptions.comparisonConditions.filter((condition) => {
    return condition.criteriaTypeId === localCriteria.value.criteriaType.criteriaTypeId
  })
})

const maxNumberValue = ref(100000)

const initialDate =
  isDateType.value && localCriteria.value.criteriaValue
    ? parseISO(localCriteria.value.criteriaValue)
    : new Date()

const selectedDay = ref(initialDate.getDate())
const selectedMonth = ref(initialDate.getMonth() + 1)
const selectedYear = ref(initialDate.getFullYear())

const days = computed(() =>
  Array.from(
    { length: getDaysInMonth(new Date(selectedYear.value, selectedMonth.value - 1)) },
    (_, i) => i + 1
  )
)
const months = computed(() => Array.from({ length: 12 }, (_, i) => format(new Date(0, i), 'MMM')))
const years = computed(() => {
  let currentYear = new Date().getFullYear()
  const yearsBefore = 25
  const yearsAfter = 25

  return Array.from(
    { length: yearsBefore + yearsAfter + 1 },
    (_, i) => currentYear - yearsBefore + i
  )
})

watch([selectedDay, selectedMonth, selectedYear], () => {
  if (isDateType.value) {
    const formattedDate = format(
      new Date(selectedYear.value, selectedMonth.value - 1, selectedDay.value),
      'yyyy-MM-dd'
    )
    localCriteria.value.criteriaValue = formattedDate
  }
})

onMounted(() => {
  isInitialLoad.value = false
})

watch(
  () => ({ ...localCriteria.value }),
  (newVal) => {
    emit('update:criteria', newVal)
  },
  { deep: true }
)

const onCriteriaTypeChange = () => {
  if (filteredConditions.value.length > 0) {
    localCriteria.value.comparisonCondition.conditionId = filteredConditions.value[0].conditionId
  } else {
    localCriteria.value.comparisonCondition.conditionId = null
  }

  if (!isInitialLoad.value) {
    localCriteria.value.criteriaValue = ''
  }

  if (isDateType.value) {
    localCriteria.value.criteriaValue = format(new Date(), 'yyyy-MM-dd')
  }

  emit('update:criteria', { ...localCriteria.value })
}

const removeCriteria = () => {
  emit('delete:criteria', props.criteria.criteriaId)
}

const enforceMaxValue = () => {
  if (localCriteria.value.criteriaValue > maxNumberValue.value) {
    localCriteria.value.criteriaValue = maxNumberValue.value
  }
}
</script>

<template>
  <div class="criteria-row">
    <div class="flex-container">
      <div class="flex-child">
        <select
          class="select-type"
          v-model="localCriteria.criteriaType.criteriaTypeId"
          @change="onCriteriaTypeChange"
        >
          <option
            v-for="type in filterStore.filterCriteriaOptions.criteriaTypes"
            :key="type.criteriaTypeId"
            :value="type.criteriaTypeId"
          >
            {{ type.typeName }}
          </option>
        </select>
      </div>

      <div class="flex-child">
        <select class="select-condition" v-model="localCriteria.comparisonCondition.conditionId">
          <option
            v-for="condition in filteredConditions"
            :key="condition.conditionId"
            :value="condition.conditionId"
          >
            {{ condition.conditionName }}
          </option>
        </select>
      </div>

      <div class="flex-child" v-if="isDateType">
        <div class="date-boxes">
          <select v-model="selectedDay">
            <option v-for="day in days" :key="day" :value="day">{{ day }}</option>
          </select>
          <select v-model="selectedMonth">
            <option v-for="(month, index) in months" :key="index" :value="index + 1">
              {{ month }}
            </option>
          </select>
          <select v-model="selectedYear">
            <option v-for="year in years" :key="year" :value="year">{{ year }}</option>
          </select>
        </div>
      </div>

      <div class="flex-child" v-else>
        <input
          v-if="inputType == 'number'"
          type="number"
          min="0"
          :max="maxNumberValue"
          @input="enforceMaxValue"
          onkeydown="return event.keyCode !== 69"
          v-model="localCriteria.criteriaValue"
        />
        <input v-if="inputType == 'text'" type="text" v-model="localCriteria.criteriaValue" />
      </div>
    </div>
    <button class="delete-btn" v-if="showDeleteButton" @click="removeCriteria">-</button>
  </div>
</template>

<style scoped>
.criteria-row {
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 6px;
}

.criteria-row select,
.criteria-row input {
  padding: 4px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.flex-container {
  display: flex;
  width: 600px;
}
.flex-child {
  flex: 1;
  min-width: 180px;
}

.select-type,
.select-condition {
  width: 180px;
}

.date-boxes {
  display: flex;
  justify-content: space-between;
  width: 188px;
}

input {
  width: 180px;
}

.delete-btn {
  width: 1.45rem;
  border: none;
  background-color: #6c6d6d;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
  color: #ccc;
}
.delete-btn:hover {
  background-color: #f13a3a;
  color: #ccc;
}
</style>
