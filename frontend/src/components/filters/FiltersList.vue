<script setup>
import FilterItem from './FilterItem.vue'

defineProps({
  filters: Array
})
</script>

<template>
  <div class="filters-list">
    <h2>Filters</h2>
    <ul>
      <FilterItem
        v-for="filter in filters"
        :key="filter.filterId"
        :filter="filter"
        @delete-filter="$emit('delete-filter', $event)"
        @edit-filter="$emit('edit-filter', $event)"
      />
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
