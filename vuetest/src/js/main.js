/*
  1:歌曲搜索接口
    请求地址:https://autumnfish.cn/search
    请求方法:get
    请求参数:keywords(查询关键字)
    响应内容:歌曲搜索结果
  2:歌曲url获取接口
    请求地址:https://autumnfish.cn/song/url
    请求方法:get
    请求参数:id(歌曲id)
    响应内容:歌曲url地址
   3.歌曲详情获取
    请求地址:https://autumnfish.cn/song/detail
    请求方法:get
    请求参数:ids(歌曲id)
    响应内容:歌曲详情(包括封面信息)
    4.热门评论获取
    请求地址:https://autumnfish.cn/comment/hot?type=0
    请求方法:get
    请求参数:id(歌曲id,地址中的type固定为0)
    响应内容:歌曲的热门评论
 */
  var app=new Vue({
    el:"#player",
    data:{
        query:"",
        musicList:[],
        musicUrl:"",
        musicPic:"",
        userComment:[]

    },
    methods:{
        selectMusic:function () {
            var that = this;
          axios.get("https://autumnfish.cn/search?keywords="+this.query)
              .then(function (response) {
                  alert("正在查询。。。")
                  //console.log(response);
                  that.musicList=response.data.result.songs;
                  //console.log(response.data.result.songs);
          })
              .catch(function (err) {

              })
        },
        playMusic:function (musicId) {
             //console.log(musicId);
            var that=this;
            axios.get("https://autumnfish.cn/song/url?id="+musicId)
                .then(function (response) {
                      //console.log(response.data.data[0].url);
                      that.musicUrl=response.data.data[0].url;
                })
                .catch(function (err) {

                })
            axios.get("https://autumnfish.cn/song/detail?ids="+musicId)
                .then(function (response) {
                     that.musicPic=response.data.songs[0].al.picUrl;
                })
                .catch(function (err) {

                })

            axios.get("https://autumnfish.cn/comment/hot?type=0&id="+musicId)
                .then(function (response) {
                    console.log(response);
                    that.userComment=response.data.hotComments;
                })
                .catch(function (err) {

                })


        }
    }
})