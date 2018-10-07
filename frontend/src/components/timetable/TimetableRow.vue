<template>
  <div class="hello">
    <button class=”Search__button”  @click="callRestService()">CALL Spring Boot REST backend service</button>

    <h3>{{ message }}</h3>
    <v-select label="employee.name" :options="employees"></v-select>
  </div>
</template>

<script>

  import axios from 'axios'

  export default {

    name: 'HelloWorld',
    data() {
      return {
        message: '',
        employees: []
      }
    },

    created: function () {
      axios.get(`/api/users`)
        .then(response => {
          this.employees = response.data
        })
        .catch(e => {
          this.errors.push(e)
        })
    },

    methods: {
      callRestService: function () {
        axios.get(`/api/hello`)
          .then(response => {
            this.message = response.data
          })
          .catch(e => {
            this.errors.push(e)
          })
      }
    }
  }
</script>

<style scoped>

</style>
