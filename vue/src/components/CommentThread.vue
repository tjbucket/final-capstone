<template>
  <div class="component">
      <h1>Comments:</h1>
      <h3 v-if="this.$store.state.activeWorkOrder.comments.length == 0">There are no comments on this order yet.</h3>
      <div id="comments-container">
          <div v-for="comment in this.$store.state.activeWorkOrder.comments" :class="$store.state.person.id == comment.posterId?'textbubble bubble-right':'textbubble bubble-left'" :key="comment.commentId" >
              <h4>{{comment.text}}</h4>
          </div>
      </div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
#comments-container{
    background-color: var(--component);
    display: flex;
    flex-direction: column;
}
.textbubble {
  max-width: 30rem;
  margin: 2.5rem;
  background: var(--text);
  padding: 1rem;
  text-align: center;
  font-weight: 900;
  color: var(--highlight);
  border-radius: 2rem 0 2rem 2rem;
  position: relative;
  overflow-wrap: break-word;
  /* border: 1rem solid var(--background); */
}

.bubble-right{
    border-radius: 2rem 0 2rem 2rem;
    align-self: flex-end;
}

.bubble-left{
    border-radius: 0 2rem 2rem 2rem;
    align-self: flex-start;
}

.bubble-right::before {
  content: "";
  width: 0px;
  height: 0px;
  position: absolute;
  border-left: 0.6rem solid var(--text);
  border-right: 0.6rem solid transparent;
  border-top: 0.6rem solid var(--text);
  border-bottom: 0.6rem solid transparent;
  right:-1rem;
  top: 0px;
}

.bubble-left::before {
  content: "";
  width: 0px;
  height: 0px;
  position: absolute;
  border-left: 0.6rem solid transparent;
  border-right: 0.6rem solid var(--text);
  border-top: 0.6rem solid var(--text);
  border-bottom: 0.6rem solid transparent;
  left: -1rem;
  top: 0px;
}
</style>