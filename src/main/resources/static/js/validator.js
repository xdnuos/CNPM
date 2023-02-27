function Validator(options) {
    function getParent(element, selector){
        while (element.parentElement){
            if(element.parentElement.matches(selector)){
                return element.parentElement
            }
            element = element.parentElement
        }
    }


    var selectorRules = {}
    //Hàm thực hiện validate

    function validate(inputElement,rule){
		var errorElement = getParent(inputElement, options.formGroupSelector).querySelector(options.errorSelector)
		var errorMessage
		
        //Lấy ra các rule của selector
        var rules = selectorRules[rule.selector]

        for( var i = 0; i < rules.length ;i++){
            switch (inputElement.type){
                case 'checkbox':
                case 'radio':
                    errorMessage = rule[i](
                        formElement.querySelector(rule.selector + 'checked')
                    );

                    break;
                default:
                    errorMessage = rules[i](inputElement.value)
            }
            
            if(errorMessage) break;

        }
        if(errorMessage){
            errorElement.innerText = errorMessage;
            getParent(inputElement, options.formGroupSelector).classList.add('invalid')
        }else{
            errorElement.innerText = '';
            getParent(inputElement, options.formGroupSelector).classList.remove('invalid')
        }

        return !errorMessage
    }

    // Lấy element của form cần validate
    var formElement = document.querySelector(options.form);
    if (formElement){
    


        // Lặp qua các rule và xử lí các sự kiện
        options.rules.forEach(function(rule){
            var inputElement = formElement.querySelector(rule.selector);
            
            if(Array.isArray(selectorRules[rule.selector])){
                selectorRules[rule.selector].push(rule.test)
            }else{
                selectorRules[rule.selector] = [rule.test]
            }


            if(inputElement){

                //Xử lí trường hợp blur khỏi input
                inputElement.onblur = function(){
                    validate(inputElement,rule)
                     

                }

                // Xử lí mỗi khi người dùng nhập vào input
                inputElement.oninput = function(){
                    var errorElement = getParent(inputElement, options.formGroupSelector).querySelector('.form-message')
                    errorElement.innerText = '';
                    getParent(inputElement, options.formGroupSelector).classList.remove('invalid')
                }   
            }
        })
    }
}


//định nghĩa các rules
//NGuyên tắc của các rules:
//1.khi có lỗi=> trả ra messages lỗi
//2. khi hợp lệ=> không trả ra cái gì cả (undefined)
Validator.isRequired = function (selector,message){
    return {
        selector : selector,
        test: function (value){
            return value ? undefined : message||'Vui lòng nhập trường này.';
        }
    };
}

Validator.isEmail = function (selector,message){
    return {
        selector : selector,
        test: function (value){
            var regex =/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/
            return regex.test(value) ? undefined : message||'Trường này phải là email.'
        } 
    };
}

Validator.minLength = function (selector,min,message){
    return {
        selector : selector,
        test: function (value){ 
            
            return value.length>= min  ? undefined : message||'Vui lòng nhập tối thiểu '+ min + ' kí tự.';
        } 
    };
}

Validator.isConfirmed = function (selector, getConfirmValue, message){
    return {
        selector : selector,
        test: function (value){
            return value == getConfirmValue() ? undefined : message ||'Password nhập vào không chính xác.';
        }
    } 
}

