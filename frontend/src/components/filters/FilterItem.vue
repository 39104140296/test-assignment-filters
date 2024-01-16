<script setup>
import { useFilterStore } from '@/store/filterStore'
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
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 4px;
  background-color: rgb(193, 197, 201);
  color: #fff;
  cursor: pointer;
  transition: background-color 0.2s;
  height: 2.5rem;
}

.filter-item:hover {
  background-color: rgb(34, 153, 238);
}

h3 {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  width: 6rem;
}
</style>
