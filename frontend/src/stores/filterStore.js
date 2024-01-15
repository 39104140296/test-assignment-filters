import { defineStore } from 'pinia'
import { getAllFilters, getFilterOptions, getFilterCriteria } from '@/services/apiService.js'

export const useFilterStore = defineStore('filter', {
  state: () => ({
    filters: [],
    filterCriteria: [],
    filterCriteriaOptions: {},
    isFilterDetailsOpen: false,
    filterDetails: {},
    isModalModeOn: true,
    isEditModeOn: true,
    isNew: false
  }),
  actions: {
    async fetchFilters() {
      this.filters = await getAllFilters()
    },
    async fetchFilterCriteriaOptions() {
      this.filterCriteriaOptions = await getFilterOptions()
    },
    async fetchFilterCriteria() {
      this.filterCriteria = await getFilterCriteria(this.filterDetails.filterId)
    },
    updateCriteria(criteriaId, updatedCriteriaData) {
      const index = this.filterCriteria.findIndex((c) => c.criteriaId === criteriaId)
      if (index !== -1) {
        this.filterCriteria[index] = { ...this.filterCriteria[index], ...updatedCriteriaData }
      }
    },
    async openFilterDetails(filter) {
      this.filterDetails = filter
      await this.fetchFilterCriteria()
      this.isFilterDetailsOpen = true
    },
    // setFilterDetails(filter) {
    //   this.filterDetails = filter
    //   this.openFilterDetails()
    // },
    closeFilterDetails() {
      this.isFilterDetailsOpen = false
    },
    toggleModalMode() {
      this.isModalModeOn = !this.isModalModeOn
    }
  }
})
