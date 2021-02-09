(()=>{
	let wishcolor = document.querySelector('.wish>i').style.color;
	let mvno = "?mvno="+document.querySelector('.mvno').value;
	let poster = "&poster="+document.querySelector('.mv_info_img>img').src;
	console.dir(wishcolor);
	let wishAdd = ()=>{
		let headerObj = new Headers();
		headerObj.append('content-type', "application/x-www-form-urlencoded");
		
		fetch("/mypage/mywishadd.do"+mvno+poster,{
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
		
		fetch("/mypage/mywishdel.do"+mvno,{
			method:"get",
			header : headerObj
		}).then(response => {
			if(response.ok){
				return response.text();
			}
		})
	}
	
	let wishchange = (changecolor)=>{
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

