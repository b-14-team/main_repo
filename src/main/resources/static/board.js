
$(document).ready(function () {
        const auth = BearereyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMmUzNGM1Yy1lNDE4LTQxZGQtYjZlZi1iNWM5YjZmOGVlZDYiLCJzdWIiOiJiMTR1c2VyQGdtYWlsLmNvbSIsImF1dGgiOiJVU0VSIiwiaWF0IjoxNzIwNzgxNjc5LCJleHAiOjE3MjA3ODg4NzksInRva2VuVHlwZSI6ImFjY2VzcyJ9.OGtAqBUFRfdJcQp_vWjn-cGjj2XpuEFbJjla-QvveaY ;

        if (auth !== undefined && auth !== '') {
            $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
                jqXHR.setRequestHeader('Authorization', auth);
            });
            }
});