document.querySelector('#all-agreement').addEventListener('click', (e)=>{
	let allcheck = document.querySelector('#all-agreement');
	let checkboxes = document.querySelectorAll('.agreement');
	
	if(allcheck.checked == true){
		checkboxes.forEach((e)=>{
			e.checked = true;
		})	
	}else{
		checkboxes.forEach((e)=>{
			e.checked = false;
		})
	}
})

let emailCheckFlg = false;

let emailCheck = ()=>{
	let userEmail = document.querySelector('#userEmail').value;
	let headerObj = new Headers();
	headerObj.append('content-type', "application/x-www-form-urlencoded");
	
	if(userEmail){
		fetch("/user/emailcheck.do",{
			method : "post",
			headers : headerObj,
			body : "userEmail="+userEmail
		}).then(response => {
			if(response.ok){
				return response.text();
			}
			throw new AsyncPageError(response.text());
		}).then((msg) => {
			if(msg == 'success'){
				emailCheckFlg = true;
				alert('사용 가능한 아이디 입니다.');
			}else{
				emailCheckFlg = false;
				alert('사용 불가능한 아이디 입니다.');
			}
		}).catch(error=>{
			error.alertMessage();
		})
	}else{
		alert("아이디를 입력하지 않으셨습니다.");
	}
}

let pwCheckFlg = false;

let pwCheck = ()=>{
	let userPw = document.querySelector('#userPw').value;
	let userPwCheck = document.querySelector('#userPwCheck').value;
	
	if(userPw){
		if(userPw == userPwCheck){
   			pwCheckFlg = true;
			alert("비밀번호가 확인되었습니다.");
   		}else{
   			pwCheckFlg = false;
			alert("비밀번호가 일치하지 않습니다.");
   		}
	}else{
		alert("비밀번호를 작성해주세요.");
	}
}

let authNumFlg = false;

let authNumCheck = ()=>{
	let authNum = document.querySelector('#authNum').value;
	let headerObj = new Headers();
	headerObj.append('content-type', "application/x-www-form-urlencoded");
	
	if(authNum){
		fetch("/user/authnumcheck.do",{
			method : "post",
			headers : headerObj,
			body : "authNum="+authNum
		}).then(response => {
			if(response.ok){
				return response.text();
			}
			throw new AsyncPageError(response.text());
		}).then((msg) => {
			if(msg == 'success'){
				authNumFlg = true;
				alert('인증이 확인되었습니다.');
			}else{
				authNumFlg = false;
				alert('인증 번호를 다시 확인해주세요.');
			}
		}).catch(error=>{
			error.alertMessage();
		})
	}else{
		alert("인증 번호를 입력해주세요.");
	}
}

function mySubmit(){
	let userEmail = document.querySelector('#userEmail').value;
	let headerObj = new Headers();
	headerObj.append('content-type', "application/x-www-form-urlencoded");
	
	fetch("/user/mailauth.do",{
		method : "post",
		headers : headerObj,
		body : "userEmail="+userEmail
	}).then(response => {
		if(response.ok){
			alert('이메일이 전송되었습니다. 인증 번호를 확인해주세요.');
		}else{
			alert('이메일 전송 에러');
		}
	}).catch(error=>{
		error.alertMessage();
	})
	
}

document.querySelector('.user-read-more').addEventListener('click',(e)=>{
	window.open("${context}/user/useragreement.do", "사용자 이용 약관", "width=500px, height=500px");
})

document.querySelector('.age-read-more').addEventListener('click',(e)=>{
	window.open("${context}/user/ageagreement.do", "사용자 이용 약관", "width=500px, height=500px");
})

document.querySelector('.frm_join').addEventListener('submit', (e)=>{
	let password = document.querySelector('#userPw').value;
	let regExp = /^(?!.*[ㄱ-힣])(?=.*\W)(?=.*\d)(?=.*[a-zA-Z])(?=.{8,})/;
	
	if(!emailCheckFlg){
		alert("이메일 중복검사를 통과하지 못했습니다.");
		document.querySelector('#userEmail').value = "";
		e.preventDefault();
	}
	
	if(!pwCheckFlg){
		alert("비밀번호를 확인해주세요.");
		document.querySelector('#userPw').value = "";
		document.querySelector('#userPwCheck').value = "";
		e.preventDefault();
	}
	
	if(!authNumFlg){
		alert("이메일 인증을 통과하지 못했습니다.");
		document.querySelector('#authNum').value = "";
		e.preventDefault();
	}
	
	if(!(regExp.test(password))){
		e.preventDefault();
		alert('비밀번호는 숫자,영문자,특수문자 조합의 8글자 이상이어야 합니다.');
		pwCheckFlg = false;
		document.querySelector('#userPw').value = '';
		document.querySelector('#userPwCheck').value = '';
	}
	
	if(!document.querySelector('#user-agreement').checked){
		alert("사용자 이용 약관에 동의해주세요.");
		e.preventDefault();
	}
	
	if(!document.querySelector('#user-age-check').checked){
		alert("만 14세 이상 확인에 동의해주세요.");
		e.preventDefault();
	}
	
});