<template>
  <div id="completion_title">
    <h3>Work Completion Time</h3>
    <Bar
      id="my-chart-id"
      :options="chartOptions"
      :data="chartData"
      v-if="checkStore"
    />
  </div>
</template>

<script>
import { Bar } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
} from "chart.js";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
);

export default {
  name: "BarChart",
  components: { Bar },
  data() {
    return {
      metric: {},
      chartData: {
        labels: this.$store.state.metric.labels,
        datasets: [
          {
            data: this.$store.state.metric.values,
            backgroundColor: ["#F4F4F9"],
            label: "Monthly Average (hours)",
            borderWidth: 1,
            borderColor: "#000000",
          },
        ],
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          x: {
            border: {
              color: "#000000",
            },
            ticks: {
              color: "#F4F4F9",
            },
            grid: {
              color: "#000000",
            },
          },
          y: {
            border: {
              color: "#000000",
            },
            ticks: {
              color: "#F4F4F9",
            },
            grid: {
              color: "#000000",
            },
          },
        },
        plugins: {
          legend: {
            labels: {
              color: "#F4F4F9",
            },
            position: "bottom",
          },
        },
      },
    };
  },
  computed: {
    checkStore() {
      // eslint-disable-next-line no-constant-condition
      while (true) {
        if (this.$store.state.metric.labels.length > 0) {
          return true;
        }
      }
    },
  },
};
</script>

<style scoped>
canvas {
  height: 300px;
}

#completion_title {
  display: flex;
  flex-direction: column;
  max-height: 30rem;
}
@media (max-width: 400px){
  h3{
    font-size: 1rem;
  }
}
</style>