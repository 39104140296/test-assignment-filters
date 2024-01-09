<script setup>
import { defineProps, defineEmits } from 'vue'
import FilterCriteria from '@/components/filters/FilterCriteria.vue'

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
</script>

<template>
  <li>
    <span>{{ filter.filterName }}</span>
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
