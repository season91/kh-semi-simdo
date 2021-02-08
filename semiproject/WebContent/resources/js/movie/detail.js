(()=>{
	let wishcolor = 'black';
	let wishAdd = ()=>{
		let headerObj = new Headers();
		headerObj.append('content-type', "application/x-www-form-urlencoded");
		
		fetch("/mypage/mywishadd.do",{
			method:"get",
			header : headerObj
		}).then(response => {
			if(response.ok){
				return response.text();
			}
		})
	}
	
	let wishDel = ()=>{
		let headerObj = new Headers();
		headerObj.append('content-type', "application/x-www-form-urlencoded");
		
		fetch("/mypage/mywishdel.do",{
			method:"get",
			header : headerObj
		}).then(response => {
			if(response.ok){
				return response.text();
			}
		})
	}
	
	let wishchange = (changecolor,isAdd)=>{
		let wish = document.querySelector('.wish>i');
		wish.style.color = changecolor;
		wishcolor = changecolor;
		
	}
	
	document.querySelector('.wish>i').addEventListener('click',()=>{
		if(wishcolor == 'black'){
			wishAdd();
			wishchange('red');
			
			
		} else {
			wishDel();
			wishchange('black');
			
		}
	})
	
	
})();

