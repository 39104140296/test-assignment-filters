<script setup>
import { defineProps } from 'vue'

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
</script>

<template>
  <div class="filters-list">
    <h2>Filters</h2>
    <ul>
      <li v-for="filter in props.filters" :key="filter.filterId">
        <span>{{ filter.filterName }}</span> - <span>{{ filter.amount }}</span> -
        <span>{{ filter.title }}</span> -
        <span>{{ formatDate(filter.createdAt) }}</span>
        <button @click="editFilter(filter.filterId)">Edit</button>
        <button @click="deleteFilter(filter.filterId)">Delete</button>
      </li>
    </ul>
  </div>
</template>

<style scoped></style>
