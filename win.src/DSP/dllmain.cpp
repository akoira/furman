// dllmain.cpp : Implementation of DllMain.

#include "stdafx.h"
#include "resource.h"
#include "DSP_i.h"
#include "dllmain.h"

CDSPModule _AtlModule;
HINSTANCE hThisInstance;

// DLL Entry Point
extern "C" BOOL WINAPI DllMain(HINSTANCE hInstance, DWORD dwReason, LPVOID lpReserved)
{
	hThisInstance = hInstance;
	return _AtlModule.DllMain(dwReason, lpReserved); 
}
