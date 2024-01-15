<script setup>
import { useFilterStore } from '@/stores/filterStore'
import FilterDetails from '@/components/filters/FilterDetails.vue'

const props = defineProps({
  filter: Object
})

const store = useFilterStore()
</script>

<template>
  <div class="filter-item" @click="store.openFilterDetails(props.filter)">
    <h3>{{ filter.filterName }}</h3>
  </div>

  <Teleport to="body">
    <FilterDetails v-if="store.isFilterDetailsOpen && store.isModalModeOn" />
  </Teleport>
</template>

<style scoped>
.filter-item {
  cursor: pointer;
  padding: 16px;
  background-color: #f5f5f5;
  border: 1px solid #e1e1e1;
  border-radius: 6px;
  transition: background-color 0.2s;
}

.filter-item:hover {
  background-color: #e2e2ff;
}
</style>
