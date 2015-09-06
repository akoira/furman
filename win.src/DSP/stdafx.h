// stdafx.h : include file for standard system include files,
// or project specific include files that are used frequently,
// but are changed infrequently

#pragma once

#ifndef STRICT
#define STRICT
#endif

#include "targetver.h"

#define _ATL_APARTMENT_THREADED
#define _ATL_NO_AUTOMATIC_NAMESPACE

#define _ATL_CSTRING_EXPLICIT_CONSTRUCTORS	// some CString constructors will be explicit

#include "resource.h"
#include <atlbase.h>
#include <atlcom.h>
#include <atlctl.h>
#include <atlsafe.h>

using namespace ATL;

extern HINSTANCE hThisInstance;

//#import "X:\\API\\ObjectARX\\18\\inc-win32\\axdb18enu.tlb" no_namespace named_guids raw_dispinterfaces raw_method_prefix("") high_method_prefix("Method")
#import "X:\\API\\ObjectARX\\18\\inc-win32\\acax18ENU.tlb" no_namespace named_guids raw_dispinterfaces raw_method_prefix("") high_method_prefix("Method")
#import "X:\\Test\\DSP\\Debug\\IMDSPSrv.arx" no_namespace named_guids raw_dispinterfaces raw_method_prefix("") high_method_prefix("Method")
