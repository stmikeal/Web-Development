function onlyDigits() {
	var separator = ",";
	var replaced = new RegExp('[^\\d\\'+separator+'\\-]', "g");
	var regex = new RegExp('\\'+separator, "g");
	console.log(this.value);
	this.value = this.value.replace(replaced, "");


	var minValue = -5;
	var maxValue = 5;
	var val = parseFloat(separator == "." ? this.value : this.value.replace(new RegExp(separator, "g"), "."));
	if (minValue <= maxValue) {
		if (this.value[0] == "-") {
			if (this.value.length > 8) {
				this.value = this.value.substr(0, 8);
			}
		} else {
			if (this.value.length > 7) {
				this.value = this.value.substr(0, 7);
			}
		}
		
		if (this.value[0] == separator) {
			this.value = "0" + this.value;
		}
		
		if (minValue < 0 && maxValue < 0) {
			if (this.value[0] != "-")
				this.value = "-" + this.value[0];
		} else if (minValue >= 0 && maxValue >= 0) {
			if (this.value[0] == "-") 
				this.value = this.value.substr(0, 0);
		}
			 
		if (val < minValue || val > maxValue) {
			this.value = this.value.substr(0, 0);
		}
		if (this.value.match(regex)) 
			if (this.value.match(regex).length > 1) {
				this.value = this.value.substr(0, 0);
			}
		
		if (this.value.match(/\-/g)) 
			if (this.value.match(/\-/g).length > 1) {
				this.value = this.value.substr(0, 0);
			}

	}
}

