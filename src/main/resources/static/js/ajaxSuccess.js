const fnJwtCreateSuccess = res => { // jwt 생성 성공
    
    $('.small.modal').modal('show'); // Model 호출

    $("#createdToken").html(res.data);

}

const fnJwtListSuccess = res => { // jwt 리스트 성공

    res.data.forEach( e => {
        let objPayloadData = `<div class="item" onclick="fnRedisItemOnclick(this)" id=item`+ e.id +`>
                            <i class="large database middle aligned icon"></i>
                            <div class="content">
                                <a class="header">` + e.token  +`</a>
                                <div class="description">` + e.time + `</div>
                            </div>
                        </div>`;

        $("#jwtList").append(objPayloadData);

    })
}

const fnJwtSaveSuccess = res => {

    alert("등록되었습니다.")
    fnJwtList();

}

const fnGetJwtSuccess = res => {
    let token = res.data.token;
    let tokenSplit = token.split(".");

    $("#jwtHeader").html(tokenSplit[0])
    $("#jwtPayload").html(tokenSplit[1])
    $("#jwtSignature").html(tokenSplit[2])
    
    $('.large.modal')
    .modal('show');
}


