"use strict";
module.exports = JSON.stringify({
	"backendPlatform": "JAVA",
	"name": "liberty-sample",
	"server": {
		"diskQuota": "1024",
		"host": "LibertyInstanceHost",
		"instances": "1",
		"memory": "256M",
		"name": "LibertyInstanceName",
		"services": [
			"ServiceInstanceName"
		]
	}
});
