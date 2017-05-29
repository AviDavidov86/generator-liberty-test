var Generator = require('yeoman-generator');
var jsyaml = require("js-yaml");
const OPTION_BLUEMIX = "bluemix";
const OPTION_STARTER = "starter";
const OPTION_BACKEND_PLATFORM = "bp";
const OPTION_DEBUG = "debug";
module.exports = class extends Generator {
	constructor(args, opts) {
		super(args, opts);
		console.log(">> Constructor");
		this.option(OPTION_BLUEMIX, {
			description: "Project configuration received from Scaffolder. Stringified JSON object.",
			type: String
		});
		this.option(OPTION_STARTER, {
			description: "Generator options received from Scaffolder, as defined in blueprint.json. Stringified JSON object.",
			type: String
		});
		this.option(OPTION_BACKEND_PLATFORM, {
			description: "Use this option to override the backendPlatform value received from Scaffolder",
			type: String
		});
		this.option(OPTION_DEBUG, {
			description: "Use this option to print options object passed to the generator",
			type: Boolean
		});
		this._sanitizeOption(this.options, OPTION_BLUEMIX);
		this._sanitizeOption(this.options, OPTION_STARTER);
		var projectConfig = this.options.bluemix || {};
		projectConfig.hasServer = projectConfig.hasOwnProperty("server") && projectConfig.server;
		projectConfig.backendPlatform = this.options.bp || projectConfig.backendPlatform || "JAVA";
		var platform = projectConfig.backendPlatform.toLowerCase();
		if (projectConfig.hasServer) {
			projectConfig.server.organization = projectConfig.server.organization || "antonal";
		}
		this._generateSampleFiles(platform, projectConfig);
	}
	_sanitizeOption(options, name) {
		if (!options[name]) {
			console.log("--" + name + " parameter was not used. Loading data from default_" + name + ".js");
			this.options[name] = JSON.parse(require("./default_" + name));
		} else {
			try {
				this.options[name] = typeof(this.options[name]) === "string" ?
					JSON.parse(this.options[name]) : this.options[name];
			} catch (e) {
				throw "--" + name + " parameter is expected to be a valid stringified JSON object";
			}
		}
	}
	_generateSampleFiles(platform, projectConfig) {
		console.log("_generateKubeFiles :: platform ::", platform);
		console.log("_generateKubeFiles :: projectConfig ::", JSON.stringify(projectConfig));
		if (!projectConfig.hasServer) {
			console.warn("No server configuration received. Skipping.");
			return;
		}

		this.fs.copyTpl(
			this.templatePath("*"),
			this.destinationPath("Liberty"),
			projectConfig
		);
	}
	method() {}
};