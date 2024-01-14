import { defineStore } from 'pinia'
import { getAllFilters, getFilterOptions, getFilterCriteria } from '@/services/apiService.js'

export const useFilterStore = defineStore('filter', {
  state: () => ({
    filters: [],
    filterCriteria: [],
    filterCriteriaOptions: {}
  }),
  actions: {
    async fetchFilters() {
      this.filters = await getAllFilters()
    },
    async fetchFilterCriteriaOptions() {
      this.filterCriteriaOptions = await getFilterOptions()
    },
    async fetchFilterCriteria(filterId) {
      const data = await getFilterCriteria(filterId)
      this.filterCriteria = data
    },
    updateCriteria(criteriaId, updatedCriteriaData) {
      const index = this.filterCriteria.findIndex((c) => c.criteriaId === criteriaId)
      if (index !== -1) {
        this.filterCriteria[index] = { ...this.filterCriteria[index], ...updatedCriteriaData }
      }
    }
  }
})
