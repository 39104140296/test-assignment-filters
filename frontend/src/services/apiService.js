import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080/api'

const axiosInstance = axios.create({
  baseURL: API_BASE_URL
})

export const getAllFiltersWithCriteria = async () => {
  try {
    const response = await axiosInstance.get('/filters/with-criteria')
    return response.data
  } catch (error) {
    console.error('Error fetching filters with criteria: ', error)
    throw error
  }
}

export const createFilter = async (filter) => {
  try {
    const response = await axiosInstance.post('/filters/', filter)
    return response.data
  } catch (error) {
    console.error('Error creating filter: ', error)
    throw error
  }
}

export const updateFilter = async (filterId, filter) => {
  try {
    const response = await axiosInstance.put(`/filters/${filterId}`, filter)
    return response.data
  } catch (error) {
    console.error('Error updating filter: ', error)
    throw error
  }
}

export const deleteFilter = async (filterId) => {
  try {
    const response = await axiosInstance.delete(`/filters/${filterId}`)
    return response.data
  } catch (error) {
    console.error('Error deleting filter: ', error)
    throw error
  }
}
