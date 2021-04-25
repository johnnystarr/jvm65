node {
	stage("Assemble") {
		sh "./gradlew assemble"
	}
	stage("Build") {
		sh "./gradlew build --scan"
	}
	stage("Test") {
		sh "./gradlew test"
	}
}