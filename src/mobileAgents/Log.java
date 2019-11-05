package mobileAgents;

public class Log {

    /**
     * message displayed in the GUI
     */
    private String logMessage;

    /**
     * initial message in the GUI
     */
    public Log() {
        logMessage = "Starting the Program";
    }

    /**
     * find the next message to be displayed
     * @param logMessage
     */
    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    /**
     * @return the message that was just found
     */
    public String getLogMessage() {
        return logMessage;
    }
}
