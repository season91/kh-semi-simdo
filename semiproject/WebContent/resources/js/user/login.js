function loginWithKakao(){
	window.Kakao.init('b140a5f5d7731e0c477c0a030751f776');
	window.Kakao.Auth.loginForm({
		scope:'profile, account_email, gender, birthday',
		success: function(authObj){
			console.log(authObj);
			window.Kakao.API.request({
				url:'/v2/user/me',
				success: res => {
					const kakao_account = res.kakao_account;
					console.log(kakao_account);
					const url = '/user/kakaologinimpl.do';
					let paramObj = new Object();
					paramObj.userEmail = res.kakao_account.email;
					paramObj.userAccessToken = Kakao.Auth.getAccessToken();
					Kakao.Auth.setAccessToken(Kakao.Auth.getAccessToken());
					
					let headerObj = new Headers();
					headerObj.append("content-type","application/x-www-form-urlencoded");
					fetch(url,{
						method : "post",
						headers : headerObj,
						body : "data="+JSON.stringify(paramObj)
					}).then(response => {
						if(response.ok){
							return response.text();
						}
						throw new AsyncPageError(response.text());
					}).then((msg) => {
						if(msg == 'success'){
							alert('카카오 계정으로 로그인에 성공했습니다.');
							location.href = "/index.do";
						}else{
							alert('카카오 계정으로 로그인에 실패했습니다.');
							location.href = "/user/login.do";
						}
					}).catch(error=>{
						error.alertMessage();
					})
				}
			});
		}
	})
}