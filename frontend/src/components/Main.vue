<template>
  <div class="main-wrapper">
    <div class="top-wrapper">
      <div class="sending-list">

        <h1>Митинги</h1>
        <div v-for="meeting in allMeetings" v-bind:key="meeting">
          <div class="submission">
            <div class="submission-info">
              <div class="submission-date-creation">
                {{meeting.dateCreation}}
              </div>
              <div>{{meeting.employee}}</div>
            </div>
            <div class="submission-wrapper">
              {{meeting.date}} {{meeting.timeStart}} {{meeting.duration}}
            </div>
          </div>
        </div>

        <div v-if="allMeetings.length == 0">
          <p>(Добавленных митингов нет)</p>
        </div>

        <button class="addMeeting-button" @click="sendMeetingsForSort(allMeetings)">Создать расписание</button>

        <hr class="border-line">

        <div>
          <h1>Расписание</h1>

          <div v-for="(meeting, index) in timetable" :key="index">
            <div v-if="func(meeting, index)">
              {{meeting.date}}
            </div>

            <div class="timetable-meeting">
              <div>{{meeting.startTime}} {{meeting.endTime}}</div>
              <div>{{meeting.employee}}</div>
            </div>

          </div>

          <hr class="border-line">

          <div>
            <div><h2>Получить расписание в конкретный день</h2></div>
            <el-date-picker
              v-model="chosenCreatedDate"
              type="date"
              placeholder="Дата митингов">
            </el-date-picker>
            <button class="addMeeting-button" @click="getTimetableInParticularDate(allMeetings)">Получить</button>
          </div>
        </div>

      </div>
      <div class="request-creation">
        <div class="creation-tittle">
          <h1>Создание митингa</h1>
        </div>
        <div class="selection-user">
          <label class="select-label">Выберите юзера</label>
          <select class="custom-dropdown" v-model="selectedEmpl">
            <option v-for="employee in employees" v-bind:value="employee.id" v-bind:key="employee.id">
              {{ employee.id }}
            </option>
          </select>
        </div>
        <div class="selection-date">
          <label class="select-label select-label-date">Выбирите дату</label>
          <el-date-picker
            v-model="chosenDate"
            type="date"
            placeholder="Дата митинга">
          </el-date-picker>
        </div>
        <div class="start-time">
          <label class="select-label select-label-date">Время начала</label>
          <el-time-picker
            v-model="selectedTimeStart"
            format="HH:mm"
            placeholder="Время митинга">
          </el-time-picker>
        </div>
        <div class="selection-meetingDurations">
          <label class="select-label">Продолжительность митинга (часов)</label>
          <select class="custom-dropdown" v-model="selectedDuration">
            <option v-for="meetingDuration in meetingDurations" v-bind:value="meetingDuration"
                    v-bind:key="meetingDuration">
              {{ meetingDuration }}
            </option>
          </select>
        </div>

        <div v-if="warning.length !== 0">
          {{warning}}
        </div>

        <div class="add-meeting">
          <button class="addMeeting-button" @click="addMeeting()">Добавить митинг</button>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
/* eslint-disable indent */

import axios from 'axios'
  import moment from 'moment'

  export default {

    components: {},

    name: 'Main',
    data() {
      return {
        employees: [{id: 'empl1'}, {id: 'empl2'}, {id: 'empl3'}, {id: 'empl4'}],
        meetingDurations: [1, 2, 3, 4, 5, 6],

        selectedEmpl: '',
        selectedDuration: '',
        chosenDate: null,
        chosenCreatedDate: null,
        selectedTimeStart: {
          hh: '',
          mm: '',
          a: 'P'
        },

        datePickerOptions: {
          format: 'YYYY-MM-DD'
        },

        timepickerOption: {
          hoursFormat: 24
        },

        allMeetings: [],

        warning: '',

        timetable: [],
        currentDateInSchedule: ''
      }
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
      },

      addMeeting: function () {

        let meeting = {
          date: moment(this.chosenDate).format('YYYY-MM-DD'),
          duration: this.selectedDuration,
          timeStart: moment(this.selectedTimeStart).format('HH:mm'),
          employee: this.selectedEmpl,
        }

        if (meeting.date && meeting.duration && meeting.timeStart && meeting.employee) {
          this.warning = ''
          this.selectedEmpl = ''
          this.selectedDuration = ''
          this.chosenDate = null
          this.selectedTimeStart = null

          this.allMeetings.push(meeting)
          return
        }

        this.warning = 'Не все поля заполнены'
      },

      sendMeetingsForSort: function (allMeetings) {
        let requestData = {
          meetings: allMeetings
        }

        axios.post(`/api/addMeetings`, requestData)
          .then(response => {
            this.timetable = response.data.meetings;
            for (let t = 0; t < this.timetable.length - 1; t++) {
              if (this.timetable[t].date === this.timetable[t + 1].date) {
                if (this.timetable[t].startTime > this.timetable[t + 1].startTime) {
                  let temp = this.timetable[t];
                  this.timetable[t] = this.timetable[t + 1];
                  this.timetable[t + 1] = temp;
                }
              }
            }
          })
          .catch(e => {
            this.errors.push(e)
          })
      },

      func: function (meeting, index) {
        if (index === 0) {
          this.currentDateInSchedule = meeting.date;

          return true;
        }
        if (meeting.date !== this.currentDateInSchedule) {
          this.currentDateInSchedule = meeting.date;
          return true;
        }
        return false;
      },

      getTimetableInParticularDate: function () {
        axios.get(`/api/getByDate?` + 'date=' + moment(this.chosenCreatedDate).format('YYYY-MM-DD'))
          .then(response => {
            this.timetable = response.data.meetings;
            for (let t = 0; t < this.timetable.length - 1; t++) {
              if (this.timetable[t].date === this.timetable[t + 1].date) {
                if (this.timetable[t].startTime > this.timetable[t + 1].startTime) {
                  let temp = this.timetable[t];
                  this.timetable[t] = this.timetable[t + 1];
                  this.timetable[t + 1] = temp;
                }
              }
            }
          })
          .catch(e => {
            this.errors.push(e)
          })
      }
    }
  }
</script>

<style scoped>

  .top-wrapper {
    display: flex;
    width: 100%;
    height: 700px;
  }

  .sending-list {
    width: 50%;
  }

  .request-creation {
    display: flex;
    flex-direction: column;
    width: 50%;
  }

  .selection-user {
    display: flex;
  }

  .custom-dropdown {
    position: relative;
    display: inline-block;
    vertical-align: middle;
  }

  .custom-dropdown select {
    background-color: #1abc9c;
    color: #fff;
    font-size: inherit;
    padding: .5em;
    padding-right: 2.5em;
    border: 0;
    margin: 0;
    border-radius: 3px;
    text-indent: 0.01px;
    text-overflow: '';
    -webkit-appearance: button; /* hide default arrow in chrome OSX */
  }

  .custom-dropdown::before,
  .custom-dropdown::after {
    content: "";
    position: absolute;
    pointer-events: none;
  }

  .selection-user {
    margin-top: 30px;
  }

  .custom-dropdown::after { /*  Custom dropdown arrow */
    content: "\25BC";
    height: 1em;
    font-size: .625em;
    line-height: 1;
    right: 1.2em;
    top: 50%;
    margin-top: -.5em;
  }

  .custom-dropdown::before { /*  Custom dropdown arrow cover */
    width: 2em;
    right: 0;
    top: 0;
    bottom: 0;
    border-radius: 0 3px 3px 0;
  }

  .custom-dropdown select[disabled] {
    color: rgba(0, 0, 0, .3);
  }

  .custom-dropdown select[disabled]::after {
    color: rgba(0, 0, 0, .1);
  }

  .custom-dropdown::before {
    background-color: rgba(0, 0, 0, .15);
  }

  .custom-dropdown::after {
    color: rgba(0, 0, 0, .4);
  }

  .select-label {
    color: #585858;
    font-size: 1.5em;
  }

  .custom-dropdown {
    width: 150px;
    margin-left: 10px;
  }

  .selection-date {
    margin-top: 20px;
    display: flex;
  }

  .selection-meetingDurations {
    margin-top: 20px;
    display: flex;
  }

  .select-label-date {
    margin-right: 10px;
  }

  .addMeeting-button {
    margin-top: 20px;
    width: 150px;
    height: 40px;
    font-size: 1em;
  }

  .start-time {
    margin-top: 20px;
    display: flex;
  }

  .submission {
    border: 1px solid gray;
    margin-bottom: 10px;
    width: 400px;
  }

  .border-line {
    margin-right: 15px;
  }

  .timetable-meeting {
    border: 1px solid gray;
    margin-bottom: 10px;
    width: 400px;
  }




</style>
