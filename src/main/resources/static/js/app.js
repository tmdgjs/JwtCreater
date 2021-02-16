$(document).ready(() => {
    

})

let state = {
    intPayloadSize : 1,
    intPayloadMaxSize : 10,
    intPayloadAddCount : 1
}


const fnObjectAdd = () => {

    if(state.intPayloadSize + 1 > state.intPayloadMaxSize){
        alert("Payload의 데이터 개수는 최대 30개입니다.")
        return;
    }

    let objPayloadData = `<li id=payload`+ state.intPayloadAddCount +`>
                            <input placeholder='Key' /> <span class='colon'>:</span>
                            <input placeholder='Value'/>
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

const fnRedisItemOnclick = (item) => {

    $('.large.modal')
    .modal('show');

    let jwt = $(item).children(".content").children(".header").html(); // 임시

    copyToClipboard(jwt);

}

const copyToClipboard = (value) => {

    var t = document.createElement("textarea");
    document.body.appendChild(t);
    t.value = value;
    t.select();
    document.execCommand('copy');
    document.body.removeChild(t);

    alert("Copy!")
      

}