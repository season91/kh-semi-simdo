let reviewDel = (idx)=>{
	let reviewNo = idx;
	let headerObj = new Headers();
	headerObj.append('content-type', "application/x-www-form-urlencoded");
	
	fetch("/mypage/reviewdelete.do",{
		method : "post",
		headers : headerObj,
		body : "reviewNo="+reviewNo
	}).then(response => {
		if(response.ok){
			return response.text();
		}
		throw new AsyncPageError(response.text());
	}).then((msg) => {
		if(msg == 'success'){
			alert('영화 후기 삭제가 완료되었습니다.');
			location.reload();
		}else{
			alert('영화 후기 삭제 중 에러가 발생했습니다.');
		}
	}).catch(error=>{
		error.alertMessage();
	})
}

let fmslineDel = (idx)=>{
	let fmslineNo = idx;
	let headerObj = new Headers();
	headerObj.append('content-type', "application/x-www-form-urlencoded");
	
	fetch("/mypage/fmslinedelete.do",{
		method : "post",
		headers : headerObj,
		body : "fmslineNo="+fmslineNo
	}).then(response => {
		if(response.ok){
			return response.text();
		}
		throw new AsyncPageError(response.text());
	}).then((msg) => {
		if(msg == 'success'){
			alert('나만의 명대사 삭제가 완료되었습니다.');
			location.reload();
		}else{
			alert('나만의 명대사 삭제 중 에러가 발생했습니다.');
		}
	}).catch(error=>{
		error.alertMessage();
	})
}

let translation = (original, lang)=>{
	let url = '/mypage/translationimpl.do';
	let text = original;
	let lan = document.getElementById(lang).value;
	let paramObj = new Object();
	paramObj.text = text;
	paramObj.lan = lan;
	
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
		if(msg == 'fail'){
			alert('번역에 실패했습니다.');
		}else{
			alert(msg);
		}
	}).catch(error=>{
		error.alertMessage();
	})
}