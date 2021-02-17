$(document).ready(() => {
    fnJwtList();

})

let state = {
    intPayloadSize : 1,
    intPayloadMaxSize : 10,
    intPayloadAddCount : 1
}

const fnDefaultAjax = (type, url, data, successFunction, errorFunction) => {

    $.ajax({
        type : type,
        contentType: 'application/json',
        url  : url,
        //async : false,
        data : JSON.stringify(data),
        success : res => {
            successFunction(res)
        },
        error : err => {
            errorFunction(err)
        },
    })

}

const fnObjectAdd = () => {

    if(state.intPayloadSize + 1 > state.intPayloadMaxSize){
        alert("Payload의 데이터 개수는 최대 30개입니다.")
        return;
    }

    let objPayloadData = `<li class='payload-item' id=payload`+ state.intPayloadAddCount +`>
                            <input class="key" placeholder='Key' /> <span class='colon'>:</span>
                            <input class="value" placeholder='Value'/>
                            <button onclick='fnObjectRemove(this)'>➖</button>
                        </li>`;

    $("#payloadDataList").append(objPayloadData);

    state.intPayloadSize++;
    state.intPayloadAddCount++;
    
}

const fnObjectRemove = (item) => {

    let parentID = "#" + $(item).parent().attr('id');

    if(parentID === "#payload0"){
        alert("이 항목은 Remove할 수 없습니다.")
        return;
    }

    $(parentID).remove();
    state.intPayloadSize--;
}

const fnJwtCreater = (type) => {

    let algorithm = $("#algorithm").val();
    let secretKey = $("#secretKey").val();
    let payload   = fnPayload();

    let objReq = new Object();

    objReq.algo = algorithm;
    objReq.secretKey = secretKey;
    objReq.data = payload;

    fnDefaultAjax("post","/jwts/"+type, objReq, fnSuccess, fnError)
}

const fnPayload = () => {

    let obj = new Map();

    for(let i=0; i < state.intPayloadSize; i++){
        let key   = $(".payload-item:eq("+i+")").children(".key").val()
        let value = $(".payload-item:eq("+i+")").children(".value").val()

        obj.set(key, value);
    }

    return Object.fromEntries(obj);
}


const fnSuccess = (res) => {

    console.log(res.data);
}


const fnError = (err) => {

    alert(err.responseJSON.msg);
}


const fnRedisItemOnclick = (item) => {

    $('.large.modal')
    .modal('show');

    let jwt = $(item).children(".content").children(".header").html(); // 임시

    copyToClipboard(jwt);

}

const fnJwtList = () => {

    fnDefaultAjax("get","/jwts", "", fnJwtListSuccess, fnJwtListError)

}

const fnJwtListSuccess = (res) => {

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


const fnJwtListError = (err) => {

    console.error(err);
}

const copyToClipboard = (value) => {

    var t = document.createElement("textarea");
    document.body.appendChild(t);
    t.value = value;
    t.select();
    document.execCommand('copy');
    document.body.removeChild(t);

    //alert("Copy!")
      

}