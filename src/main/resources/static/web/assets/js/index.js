const { createApp } = Vue

const options = {
  data() {
    return {
      isLoggedIn: false,
      showDropdown: false,
      error: '',
    
    } 
  },
  created() {
    this.loadData();
  }, 

  methods: {
    loadData() {
      this.checkLogin() || false;  
    },
    checkLogin() {
      axios.get('/api/clients/current')
        .then(response => {
          if (response.data.role == "CLIENT" || response.data.role == "ADMIN") {
            this.isLoggedIn = true;
            console.log(this.isLoggedIn);
          }
          else {
            this.isLoggedIn = false;
            console.log(this.isLoggedIn);
          }
        })
        .catch(error => {
          console.error("Error loading user data:", error);
        });
    },
    toggleDropdown() {
      this.showDropdown = !this.showDropdown;
    },
    logout() {
      axios.post("/api/logout")
          .then(response => {
              window.location.href = "/index.html";
          })
          .catch(error => {
              console.log(error);
          });
    } 
  }
}

const app = createApp(options)
app.mount('#app')