/**
 * 
 */
 function fn_submit(){
	var fn = document.frm;
	
	if(fn.custname.value == ""){ //input에 값이 빈값인지 확인체크
		alert("회원성명이 입력되지 않았습니다.");
		fn.custname.focus(); //포커싱추가
		return false; //함수 끝내기
	}
	
	if(fn.phone.value == ""){ //input에 값이 빈값인지 확인체크
		alert("회원전화이 입력되지 않았습니다.");
		fn.phone.focus(); //포커싱추가
		return false; //함수 끝내기
	}
	
	if(fn.address.value == ""){ //input에 값이 빈값인지 확인체크
		alert("회원주소이 입력되지 않았습니다.");
		fn.address.focus(); //포커싱추가
		return false; //함수 끝내기
	}
	
	if(fn.joindate.value == ""){ //input에 값이 빈값인지 확인체크
		alert("가입일자 입력되지 않았습니다.");
		fn.joindate.focus(); //포커싱추가
		return false; //함수 끝내기
	}
	
	if(fn.grade.value == ""){ //input에 값이 빈값인지 확인체크
		alert("회원등금이 입력되지 않았습니다.");
		fn.grade.focus(); //포커싱추가
		return false; //함수 끝내기
	}
	
	if(fn.city.value == ""){ //input에 값이 빈값인지 확인체크
		alert("도시코드가 입력되지 않았습니다.");
		fn.city.focus(); //포커싱추가
		return false; //함수 끝내기
	}
	
	fn.submit();//컨트롤러(서버)에 전송
	
	
}