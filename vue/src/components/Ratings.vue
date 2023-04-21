<template>
  <div id="doughnut_title">
    <h3>All Reviews (Past 180 Days)</h3>
    <Doughnut
      id="my-chart-id"
      :options="chartOptions"
      :data="chartData"
      v-if="checkStore"
    />
  </div>
</template>

<script>
import { Doughnut } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  CategoryScale,
} from "chart.js";

ChartJS.register(Title, Tooltip, Legend, ArcElement, CategoryScale);

export default {
  name: "DoughnutChart",
  components: { Doughnut },
  data() {
    return {
      chartData: {
        labels: [
          "1-Star Rating",
          "2-Star Rating",
          "3-Star Rating",
          "4-Star Rating",
          "5-Star Rating",
        ],
        datasets: [
          {
            data: this.$store.state.ratings,
            backgroundColor: [
              "#EE6055",
              "#FF9B85",
              "#FFD97D",
              "#AAF683",
              "#60D394",
            ],
            label: "# of Reviews",
          },
        ],
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
        borderWidth: 1,
        borderColor: "#000000",
        plugins: {
          legend: {
            reverse: true,
            labels: {
              color: "#F4F4F9",
            },
          },
          title: {
            display: false,
            text: "Work Order Reviews (past 6 months)",
            color: "#F4F4F9",
          },
        },
      },
    };
  },
  computed: {
    checkStore() {
      // eslint-disable-next-line no-constant-condition
      while (true) {
        if (this.$store.state.ratings.length > 0) {
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

#doughnut_title {
  display: flex;
  flex-direction: column;
  max-height: 30rem;
  padding-bottom: 50px;
}
@media (max-width: 400px){
  h3{
    font-size: 1rem;
  }
}
</style>