(()=>{
	let list = document.querySelector('.navi');
	let check = 'no';
	
	
	let nationExtendView = ()=>{
		let nation = ['대한민국','일본','미국','영국','프랑스','중국','러시아','독일','스페인'];
		createElement(nation,"nation");
	}
	
	let genreExtendView = ()=>{
		let genre = ['드라마','액션','스릴러','가족','SF','판타지'];
		createElement(genre,"genre");
	}
	
	let createElement = (extend, paramName)=>{
		let div = document.createElement('div');
		console.dir(paramName);
		div.className = 'navi-extend-wrapper';
		let ul = document.createElement('ul');
		
		for(let i = 0; i < extend.length; i++){
			let a = document.createElement('a');
			a.className = "navi-extend-list";
			a.innerHTML = extend[i];
			a.href = "/movie/naviview.do?"+paramName+"="+extend[i];
			ul.appendChild(a);
		}
		div.append(ul);
		list.appendChild(div);

	}
	
	
	let delView = () =>{
		document.querySelector('.navi-extend-wrapper').outerHTML = '';
	}
	
	document.querySelector('.nation-view').addEventListener('click',()=>{
		
		if(check == 'no'){
			nationExtendView();
			check = 'yes';
		} else {
			delView();
			check = 'no';
		}

	});
	
	document.querySelector('.genre-view').addEventListener('click',()=>{
		if(check == 'no'){
			genreExtendView();
			check = 'yes';
		} else {
			delView();
			check = 'no';
		}
	});
	
})();