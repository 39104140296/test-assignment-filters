import axios from 'axios'

const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080/api/filters'
})

export const getAllFilters = async () => {
  try {
    const response = await axiosInstance.get()
    return response.data
  } catch (error) {
    console.error('Error getting all filters: ', error)
    throw error
  }
}

export const getFilterCriteria = async (filterId) => {
  try {
    const response = await axiosInstance.get(`/${filterId}`)
    return response.data
  } catch (error) {
    console.error(`Error getting criteria for filter ${filterId}: `, error)
    throw error
  }
}

export const updateFilter = async (filterId, filterData) => {
  try {
    const response = await axiosInstance.put(`/${filterId}`, {
      filterName: filterData.filterName,
      criteria: filterData.filterCriteria
    })
    return response.data
  } catch (error) {
    console.error('Error updating filter: ', error)
    throw error
  }
}

export const createFilter = async (newFilterData) => {
  try {
    const response = await axiosInstance.post('', newFilterData)
    return response.data
  } catch (error) {
    console.error('Error creating new filter: ', error)
    throw error
  }
}

export const deleteFilter = async (filterId) => {
  try {
    const response = await axiosInstance.delete(`/${filterId}`)
    return response.data
  } catch (error) {
    console.error('Error deleting filter: ', error)
    throw error
  }
}

export const getFilterOptions = async () => {
  try {
    const response = await axiosInstance.get('/filter-options')
    return response.data
  } catch (error) {
    console.error('Error fetching filter options: ', error)
    throw error
  }
}
