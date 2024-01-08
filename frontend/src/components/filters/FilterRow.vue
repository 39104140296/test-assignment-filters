<script setup>
import { defineProps, ref, watch } from 'vue'

const props = defineProps({
  filterId: Number
})
const emit = defineEmits(['deleteRow'])

const criteriaTypes = ['Amount', 'Title', 'Date']
const comparisonConditions = ref({
  Amount: ['More', 'Less', 'Equal'],
  Title: ['Contains', 'Starts With', 'Ends With', 'Equals'],
  Date: ['From', 'To', 'Equal']
})
const selectedCriteria = ref('Amount')
const selectedCondition = ref(comparisonConditions.value[selectedCriteria.value][0])
const value = ref('')

watch(selectedCriteria, (newCriteria) => {
  selectedCondition.value = comparisonConditions.value[newCriteria][0]
})

const deleteRow = () => {
  emit('deleteRow', props.filterId)
}
</script>

<template>
  <div class="filter-row">
    <select v-model="selectedCriteria">
      <option v-for="type in criteriaTypes" :key="type" :value="type">{{ type }}</option>
    </select>

    <select v-model="selectedCondition">
      <option
        v-for="condition in comparisonConditions[selectedCriteria]"
        :key="condition"
        :value="condition"
      >
        {{ condition }}
      </option>
    </select>

    <input
      v-if="selectedCriteria === 'Amount' || selectedCriteria === 'Title'"
      v-model="value"
      :type="selectedCriteria === 'Amount' ? 'number' : 'text'"
    />

    <div v-if="selectedCriteria === 'Date'"></div>

    <button @click="deleteRow">Delete</button>
  </div>
</template>

<style scoped>
.filter-row {
}
</style>
