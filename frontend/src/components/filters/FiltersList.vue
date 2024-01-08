<script setup>
import { format as formatDate } from 'date-fns'

const props = defineProps({
  filters: Array
})

const emit = defineEmits(['delete-filter', 'edit-filter'])

const deleteFilter = (filterId) => {
  emit('delete-filter', filterId)
}

const editFilter = (filterId) => {
  emit('edit-filter', filterId)
}

const formatDateForDisplay = (dateString) => {
  const date = new Date(dateString)
  return formatDate(date, 'PPP')
}
</script>

<template>
  <div class="filters-list">
    <h2>Filters</h2>
    <ul>
      <li v-for="filter in props.filters" :key="filter.filterId">
        <span
          >{{ filter.filterName }} (Created at: {{ formatDateForDisplay(filter.createdAt) }})</span
        >
        <ul>
          <li v-for="criteria in filter.filterCriteriaList" :key="criteria.criteriaId">
            <span>
              <div>{{ criteria.criteriaType.typeName }}</div>
              <div>{{ criteria.comparisonCondition.conditionName }}</div>
              <div>{{ criteria.value }}</div>
            </span>
          </li>
        </ul>
        <button @click="editFilter(filter.filterId)">Edit</button>
        <button @click="deleteFilter(filter.filterId)">Delete</button>
      </li>
    </ul>
  </div>
</template>

<style scoped>
.filters-list ul {
  list-style-type: none;
  padding: 0;
}

.filters-list li {
  margin-bottom: 10px;
}

.filters-list li ul li {
  margin-top: 5px;
  font-size: 0.9em;
}
</style>
