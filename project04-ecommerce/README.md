# ecommerce
This is a project submission for [Udacity JDND project 4](https://github.com/udacity/JDND/tree/master/projects/P04-eCommerce%20Application/starter_code).

## Structure
### Authentication and Authorization
The functions are as follows.
- Logged in, user profile
- Cart details
- Purchase History
- Password should check some length requirement and a confirmation field in the request to check for typos

### Metrics, Dashboards and Alerts
The dashboard screenshots are below.
- [Dashboard UI](poc/splunk/dashboard-ui.png)
- [Dashboard source](poc/splunk/dashboard-source.png)

Two alerts create created, and the screenshots are as follows.
- [success rate per minute of user creation](poc/splunk/alert1-creation)
- [success rate per minute of order creation](poc/splunk/alert2-creation)

### CI/CD
CI/CD is implemented using jenkins. The screenshot and build log is below.
- [build log](poc/jenkins/jenkins-build.log)
- [screenshot](poc/jenkins/jenkins-build.png)
