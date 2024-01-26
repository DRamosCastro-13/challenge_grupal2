const {createApp} = Vue

let app = createApp({
    data(){
        return {
			firstName: '',
			lastName: '',
			emailReg: '',
			passwordReg: '',
			dni: '',
			errorReg: '',
			email: '',
			password: '',
           
        }
    },
    created(){
        
        
    },

    methods: {
		login(email, password){
            if(!email || !password){
                this.error = "Please fill in all fields";
                return;
            }
            axios.post("/api/login?email=" + email + "&password=" + password)
            .then(response => {
                console.log(response)

                if (email.includes("@adminanb.com")) {
                    window.location.href = "/web/admin/managerOverview.html";
                } else {
                    window.location.href = "/web/pages/accounts.html";
                }
    
                this.clearData();
            })
            .catch(error => {
            this.error = error.response.data,
            console.log(error)
            })
        },
        loginEvent(){
            this.login(this.email, this.password)
        },
		validatePassword(password) {
            const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
      
            return passwordRegex.test(password);
          },
        registerClient(){
			if(!this.firstName || !this.lastName || !this.emailReg || !this.passwordReg || !this.dni) {
                this.errorReg = "Please fill in all fields";
                return;
            }

            if (!this.validatePassword(this.passwordReg)) {
                this.error = "Password must have at least 8 characters, one uppercase letter, one lowercase letter, one number, and one special character.";
                return;
              }
            axios.post('https://wire-it.onrender.com/',{
				"firstName" : this.firstName,
				"lastName" : this.lastName,
				"email" : this.email,
				"password" : this.password,
				"dni" : this.dni
			})
            .then(response => {
                Swal.fire({
                    title: "Success",
                    text: "Registration completed successfully",
                    icon: "success"
                });

                setTimeout(() => {
                    window.location.href = "../admin/overviewAdmin.html"
                }, 2000);

            })
            .catch(
                error => {
                console.log(error)
                this.errorReg = error.response.data
            })
        }
    }
}).mount('#app')
