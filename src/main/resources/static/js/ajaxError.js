const fnJwtCreateError = err => { // jwt 생성 실패

    alert(err.responseJSON.msg);
}

const fnJwtListError = err => { // jwt 리스트 실패

    console.log(err);
}

const fnJwtSaveError = err => { // jwt 저장 실패

    console.log(err);
}

const fnGetJwtError = err => {
    console.log(err);
}