const deleteBtnCollection = document.getElementsByClassName("deleteBtn");
const deleteBtns = [...deleteBtnCollection];

deleteBtns.map((deleteEl) => {
    const id = deleteEl.getAttribute("data-id");
    deleteEl.addEventListener("click", () => {
        if(window.confirm("本当に削除してもよろしいですか？")){
            document.getElementById(`deleteForm_${id}`).submit();
        }
    }) 
})
