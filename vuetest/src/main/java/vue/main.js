/*新实时段子
https://api.apiopen.top/getJoke?page=1&count=2&type=video

 */
 var  app=new Vue({
       el:"#app",
       data:{
         duanzi:'',
           duanzilist:[]
       },
      methods:{
       Tianqi:function () {
           alert("正在查询。。。")
           var that=this;
           axios.get('https://api.apiopen.top/getJoke?page=1&count=2&text='+this.duanzi)
               .then(function (response) {
                   console.log(response.data.result);
                   that.duanzilist=response.data.result;
           })
               .catch(function (err) {
           })
       },
          changeType:function (name) {
           this.name=name;
           this.Tianqi();

          }
      }
 })