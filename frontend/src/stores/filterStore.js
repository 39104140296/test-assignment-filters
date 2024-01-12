import { defineStore } from 'pinia'
import {
  findAllCriteriaTypes,
  findAllComparisonConditions,
  getFilterCriteria
} from '@/services/apiService.js'

export const useFilterStore = defineStore('filter', {
  state: () => ({
    criteriaTypes: null,
    comparisonConditions: null,
    filterCriteria: []
  }),
  actions: {
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
