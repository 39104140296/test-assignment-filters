<script setup>
import { defineProps, defineEmits } from 'vue'
import FilterCriteria from '@/components/FilterCriteria.vue'
import { format as formatDate } from 'date-fns'

const props = defineProps({
  filter: Object
})

const emit = defineEmits(['delete-filter', 'edit-filter'])

const deleteFilter = () => {
  emit('delete-filter', props.filter.filterId)
}

const editFilter = () => {
  emit('edit-filter', props.filter.filterId)
}

const formatDateForDisplay = (dateString) => {
  const date = new Date(dateString)
  return formatDate(date, 'PPP')
}
</script>

<template>
  <li>
    <span>{{ filter.filterName }} (Created at: {{ formatDateForDisplay(filter.createdAt) }})</span>
    <ul>
      <FilterCriteria
        v-for="criteria in filter.filterCriteriaList"
        :key="criteria.criteriaId"
        :criteria="criteria"
      />
    </ul>
    <button @click="editFilter">Edit</button>
    <button @click="deleteFilter">Delete</button>
  </li>
</template>

<style scoped></style>
