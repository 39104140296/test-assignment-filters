<script setup>
import { ref, computed } from 'vue'
import { getDaysInMonth } from 'date-fns'

const props = defineProps({
  criteria: Object,
  mode: {
    type: String,
    default: 'edit'
  }
})

const isNewCriterion = props.mode === 'new'
const criteriaTypes = ['Amount', 'Title', 'Date']
const comparisonOptions = {
  Amount: ['More', 'Less', 'Equal'],
  Title: ['Contains', 'Starts with', 'Ends with', 'Equals'],
  Date: ['From', 'To', 'Equal']
}

const selectedCriteriaType = ref(isNewCriterion ? 'Amount' : props.criteria.criteriaType.typeName)
const selectedComparisonCondition = ref(
  isNewCriterion ? comparisonOptions['Amount'][0] : props.criteria.comparisonCondition.conditionName
)

const textOrNumberValue = ref(isNewCriterion ? '' : props.criteria.value)
const dateValue = ref(
  isNewCriterion
    ? {
        day: new Date().getDate(),
        month: new Date().getMonth() + 1,
        year: new Date().getFullYear()
      }
    : selectedCriteriaType.value === 'Date'
      ? props.criteria.value
      : {}
)

const inputValue = computed({
  get() {
    return selectedCriteriaType.value === 'Date' ? dateValue.value : textOrNumberValue.value
  },
  set(newValue) {
    if (selectedCriteriaType.value === 'Date' && typeof newValue === 'object') {
      dateValue.value = newValue
    } else {
      textOrNumberValue.value = newValue
    }
  }
})

const daysInMonth = computed(() => {
  if (selectedCriteriaType.value === 'Date') {
    const monthIndex = dateValue.value.month - 1
    const year = dateValue.value.year
    return Array.from({ length: getDaysInMonth(new Date(year, monthIndex)) }, (_, i) => i + 1)
  }
  return Array.from({ length: 31 }, (_, i) => i + 1)
})

const startYear = 2000
const endYear = new Date().getFullYear()
const yearRange = Array.from(
  { length: endYear - startYear + 1 },
  (value, index) => startYear + index
)
</script>

<template>
  <div class="criteria-row">
    <select v-model="selectedCriteriaType">
      <option v-for="type in criteriaTypes" :key="type" :value="type">{{ type }}</option>
    </select>
    <select v-model="selectedComparisonCondition">
      <option
        v-for="option in comparisonOptions[selectedCriteriaType]"
        :key="option"
        :value="option"
      >
        {{ option }}
      </option>
    </select>

    <div v-if="selectedCriteriaType === 'Amount'">
      <input type="number" v-model="inputValue" />
    </div>
    <div v-else-if="selectedCriteriaType === 'Title'">
      <input type="text" v-model="inputValue" />
    </div>
    <div v-else-if="selectedCriteriaType === 'Date'">
      <select v-model="inputValue.day">
        <option v-for="day in daysInMonth" :key="day" :value="day">{{ day }}</option>
      </select>
      <select v-model="inputValue.month" @change="updateDays">
        <option v-for="month in 12" :key="month" :value="month">{{ month }}</option>
      </select>
      <select v-model="inputValue.year" @change="updateDays">
        <option v-for="year in yearRange" :key="year" :value="year">{{ year }}</option>
      </select>
    </div>
  </div>
</template>

<style scoped>
.criteria-row {
  display: flex;
  justify-content: space-between;
  padding: 0.5rem;
}
.criteria-row > * {
  margin: 0 0.5rem;
}
</style>
