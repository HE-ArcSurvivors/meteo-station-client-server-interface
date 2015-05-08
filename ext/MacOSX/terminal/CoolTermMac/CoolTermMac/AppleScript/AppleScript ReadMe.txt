AppleScript Support in CoolTerm 1.4.0
=====================================
Verision 1.4.0 of CoolTerm includes the following AppleScript Commands:

- LoadSetting
- CloseWindow
- Connect
- Disconnect
- Write
- WriteLine
- Poll
- Read
- ReadAll
- BytesAvailable
- LookAhead
- ClearBuffer
- Hex2Str
- Str2Hex

AppleScript for Coolterm supports multiple terminal windows, i.e. commands that interact with terminal windows need to be addressed to the intended window by passing the name of the window as the first command argument.
The syntax, arguments, and return parameters for each command are documented below.


LoadSetting(FilePath as String) as Boolean
------------------------------------------
Loads the connection setttings file with the specified path. FilePath can be a relative or absolute path.
Returns TRUE if the connection settings file was loaded successfully. The window name of the new terminal window will be identical to the file name of the settings file.

E.g.: 	if LoadSetting ("Macintosh HD:MySettings.stc") then		display alert "The settings have been loaded successfully."	else		display alert "The settings could not be loaded."	end if

CloseWindow(TerminalWindowName as string)
-----------------------------------------
Closes the terminal window with the specified name.

E.g.:	CloseWindow ("MySettings.stc")


Connect(TerminalWindowName as String) as Boolean
------------------------------------------------
Opens the serial port associated with the terminal window with the specified name.
Returns TRUE if the port was successfully opened.

E.g.:	if Connect ("MySettings.stc") then
		display alert "The port is open."
	else
		display alert "The port could not be opened."
	end if

Disconnect(TerminalWindowName as String)
----------------------------------------
Closes the serial port associated with the terminal window with the specified name.

E.g.:	Disconnect ("MySettings.stc")


Write(TerminalWindowName as String, Data as String)
---------------------------------------------------
Writes data to the serial port associated with the terminal window with the specified name.

E.g.:	Write {"MySettings.stc", "Hello Coolterm"}


WriteLine(TerminalWindowName as String, Data as String)
-------------------------------------------------------
Writes data terminated by the "Enter Key Emulation" character specified in the connection settings to the serial port associated with the terminal window with the specified name.

E.g.:	Write {"MySettings.stc", "Writing just one line."}


Poll(TerminalWindowName as String)
----------------------------------
Polls the serial port associated with the terminal window with the specified name and causes all data currently available in the serial port receive buffer to be transferred to CoolTerm's receive buffer. It is recommended to call this method before calling Read, ReadAll, LookAhead, and BytesAvailable.

E.g.:	Poll ("MySettings.stc")


Read(TerminalWindowName as String, NumChars as Integer) as String
-----------------------------------------------------------------
Reads and removes the specified number of characters from the receive buffer of the terminal window with the specified name.

E.g.:	local txt
	set txt to Read {"MySettings.stc", 5}


ReadAll(TerminalWindowName as string) as String
-----------------------------------------------
Reads and removes all characters from the receive buffer of the terminal window with the specified name.

E.g.:	local txt
	set txt to ReadAll ("MySettings.stc")


BytesAvailable(TerminalWindowName as string) as Integer
-------------------------------------------------------
Returns the number of characters currently available in the receive buffer of the terminal window with the specified name.

E.g.:	local i
	set i to BytesAvailable ("MySettings.stc")


LookAhead(TerminalWindowName as string) as String
-------------------------------------------------
Returns the contents of the receive buffer of the terminal window with the specified name without removing any data.

E.g.:	E.g.:	local txt
	set txt to LookAhead ("MySettings.stc")


ClearBuffer(TerminalWindowName as string)
-----------------------------------------
Clears receive buffer of the terminal window with the specified name.

E.g.:	ClearBuffer ("MySettings.stc")


Hex2Str(HexStr as String) as String
-----------------------------------
Converts a hexadecimal string to a plain string that can be sent to a serial port.

E.g.:	set txt to Hex2Str ("20 A5 FF 00")


Str2Hex(PlainStr as String) as String
-------------------------------------
Converts a plain string to a hexadecimal string that can be used to display non-printable characters received from a serial port.

E.g.:	display alert ("I received: " & (Str2Hex (txt)))
