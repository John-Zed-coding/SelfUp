$(function () {
    $('#login').click(function(){
        $.post("http://localhost/index.jsp",
            {
                username:$('#username').val(),
                password:$('#password').val()
            },
            function(data,status){
                alert("数据: \n" + data + "\n状态: " + status);
            });
    });
});
