package com.epam.totalizator.command;

/**
 * Enum, from where necessary command is chose.
 *
 */
public enum CommandEnum {
	LOGIN(new LoginCommand()),
	LOGOUT(new LogOutCommand()),
	REGISTER(new RegisterCommand()),
	LANG(new LanguageCommand()),
	USER_UPDATE(new UserDataUpdateCommand()),
	PASSWORD_CHANGE(new PasswordChangeCommand()),
	ADD_COMPETITION(new AddCompetitionCommand()),
	ADD_TEAM(new AddTeamCommand()),
	ADD_FORECAST(new AddForecastCommand()),
	ADD(new AddCommand()),
	SET_POOL(new SetPoolCommand());
	
	private AbstractCommand command;
	
	private CommandEnum(AbstractCommand com) {
		command = com;
	}
	
	public AbstractCommand getCommand() {
		return command;
	}
}
