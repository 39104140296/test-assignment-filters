<script setup>
import { ref, computed } from 'vue'
import { getDaysInMonth } from 'date-fns'

defineProps({
  criteria: Object
})

const criteriaTypes = ['Amount', 'Title', 'Date']
const comparisonOptions = {
  Amount: ['More', 'Less', 'Equal'],
  Title: ['Contains', 'Starts with', 'Ends with', 'Equals'],
  Date: ['From', 'To', 'Equal']
}

const selectedCriteriaType = ref('Amount')
const selectedComparisonCondition = ref(comparisonOptions[selectedCriteriaType.value][0])
const inputValue = ref({
  day: new Date().getDate(),
  month: new Date().getMonth() + 1,
  year: new Date().getFullYear()
})

const daysInMonth = computed(() => {
  const days = getDaysInMonth(new Date(inputValue.value.year, inputValue.value.month - 1))
  return Array.from({ length: days }, (_, i) => i + 1)
})

const updateDays = () => {
  const newDaysInMonth = getDaysInMonth(new Date(inputValue.value.year, inputValue.value.month - 1))
  if (inputValue.value.day > newDaysInMonth) {
    inputValue.value.day = newDaysInMonth
  }
}

const startYear = 2000
const endYear = new Date().getFullYear()
const yearRange = []
for (let year = startYear; year <= endYear; year++) {
  yearRange.push(year)
}
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
