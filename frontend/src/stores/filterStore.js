import { defineStore } from 'pinia'
import {
  getAllFilters,
  findAllCriteriaTypes,
  findAllComparisonConditions,
  getFilterCriteria
} from '@/services/apiService.js'

export const useFilterStore = defineStore('filter', {
  state: () => ({
    filters: [],
    criteriaTypes: null,
    comparisonConditions: null,
    filterCriteria: []
  }),
  actions: {
    async fetchFilters() {
      this.filters = await getAllFilters()
    },
    async fetchCriteriaTypes() {
      this.criteriaTypes = await findAllCriteriaTypes()
    },
    async fetchComparisonConditions() {
      this.comparisonConditions = await findAllComparisonConditions()
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
