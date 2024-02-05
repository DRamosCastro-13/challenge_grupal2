const { createApp } = Vue

const options = {
  data() {
    return {
      isLoggedIn: false,
      showDropdown: false,
      error: '',
    
    } // finaliza return
  }, // finaliza data
  created() {
    this.checkLogin()
  }, //finaliza created

  methods: {
    checkLogin() {
      axios.get('/api/clients/current')
        .then(response => {
          if (response.data.role == "CLIENT" || response.data.role == "ADMIN") {
            this.isLoggedIn = true;
          }
          else {
            this.isLoggedIn = false;
          }
        })
        .catch(error => {
          console.error("Error loading user data, please login", error);
        });
    },
    logout() {
      axios.post("/api/logout")
          .then(response => {
              window.location.href = "/index.html";
          })
          .catch(error => {
              console.log(error);
          });
    },
    toggleDropdown() {
      this.showDropdown = !this.showDropdown;
    }
  }
}

const app = createApp(options)
app.mount('#app')