

/* this ALWAYS GENERATED file contains the definitions for the interfaces */


 /* File created by MIDL compiler version 7.00.0500 */
/* at Fri Oct 01 10:05:05 2010
 */
/* Compiler settings for .\DSP.idl:
    Oicf, W1, Zp8, env=Win32 (32b run)
    protocol : dce , ms_ext, c_ext, robust
    error checks: stub_data 
    VC __declspec() decoration level: 
         __declspec(uuid()), __declspec(selectany), __declspec(novtable)
         DECLSPEC_UUID(), MIDL_INTERFACE()
*/
//@@MIDL_FILE_HEADING(  )

#pragma warning( disable: 4049 )  /* more than 64k source lines */


/* verify that the <rpcndr.h> version is high enough to compile this file*/
#ifndef __REQUIRED_RPCNDR_H_VERSION__
#define __REQUIRED_RPCNDR_H_VERSION__ 475
#endif

#include "rpc.h"
#include "rpcndr.h"

#ifndef __RPCNDR_H_VERSION__
#error this stub requires an updated version of <rpcndr.h>
#endif // __RPCNDR_H_VERSION__

#ifndef COM_NO_WINDOWS_H
#include "windows.h"
#include "ole2.h"
#endif /*COM_NO_WINDOWS_H*/

#ifndef __DSP_i_h__
#define __DSP_i_h__

#if defined(_MSC_VER) && (_MSC_VER >= 1020)
#pragma once
#endif

/* Forward Declarations */ 

#ifndef __IApp_FWD_DEFINED__
#define __IApp_FWD_DEFINED__
typedef interface IApp IApp;
#endif 	/* __IApp_FWD_DEFINED__ */


#ifndef __App_FWD_DEFINED__
#define __App_FWD_DEFINED__

#ifdef __cplusplus
typedef class App App;
#else
typedef struct App App;
#endif /* __cplusplus */

#endif 	/* __App_FWD_DEFINED__ */


/* header files for imported files */
#include "oaidl.h"
#include "ocidl.h"

#ifdef __cplusplus
extern "C"{
#endif 


#ifndef __IApp_INTERFACE_DEFINED__
#define __IApp_INTERFACE_DEFINED__

/* interface IApp */
/* [unique][helpstring][nonextensible][dual][uuid][object] */ 


EXTERN_C const IID IID_IApp;

#if defined(__cplusplus) && !defined(CINTERFACE)
    
    MIDL_INTERFACE("277DA9A4-8DE6-4D1D-87ED-7A71B941F3C1")
    IApp : public IDispatch
    {
    public:
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE CreateDWG( 
            /* [in] */ BSTR FullPath,
            /* [in] */ DOUBLE MinX,
            /* [in] */ DOUBLE MinY,
            /* [in] */ DOUBLE MaxX,
            /* [in] */ DOUBLE MaxY) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE GetData( 
            /* [in] */ BSTR DWGFullPath,
            /* [out] */ DOUBLE *LineLength,
            /* [out] */ DOUBLE *CurveLength,
            /* [out] */ DOUBLE *Square,
            /* [out] */ LONG *RegionCount) = 0;
        
    };
    
#else 	/* C style interface */

    typedef struct IAppVtbl
    {
        BEGIN_INTERFACE
        
        HRESULT ( STDMETHODCALLTYPE *QueryInterface )( 
            IApp * This,
            /* [in] */ REFIID riid,
            /* [iid_is][out] */ 
            __RPC__deref_out  void **ppvObject);
        
        ULONG ( STDMETHODCALLTYPE *AddRef )( 
            IApp * This);
        
        ULONG ( STDMETHODCALLTYPE *Release )( 
            IApp * This);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfoCount )( 
            IApp * This,
            /* [out] */ UINT *pctinfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfo )( 
            IApp * This,
            /* [in] */ UINT iTInfo,
            /* [in] */ LCID lcid,
            /* [out] */ ITypeInfo **ppTInfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetIDsOfNames )( 
            IApp * This,
            /* [in] */ REFIID riid,
            /* [size_is][in] */ LPOLESTR *rgszNames,
            /* [range][in] */ UINT cNames,
            /* [in] */ LCID lcid,
            /* [size_is][out] */ DISPID *rgDispId);
        
        /* [local] */ HRESULT ( STDMETHODCALLTYPE *Invoke )( 
            IApp * This,
            /* [in] */ DISPID dispIdMember,
            /* [in] */ REFIID riid,
            /* [in] */ LCID lcid,
            /* [in] */ WORD wFlags,
            /* [out][in] */ DISPPARAMS *pDispParams,
            /* [out] */ VARIANT *pVarResult,
            /* [out] */ EXCEPINFO *pExcepInfo,
            /* [out] */ UINT *puArgErr);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *CreateDWG )( 
            IApp * This,
            /* [in] */ BSTR FullPath,
            /* [in] */ DOUBLE MinX,
            /* [in] */ DOUBLE MinY,
            /* [in] */ DOUBLE MaxX,
            /* [in] */ DOUBLE MaxY);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *GetData )( 
            IApp * This,
            /* [in] */ BSTR DWGFullPath,
            /* [out] */ DOUBLE *LineLength,
            /* [out] */ DOUBLE *CurveLength,
            /* [out] */ DOUBLE *Square,
            /* [out] */ LONG *RegionCount);
        
        END_INTERFACE
    } IAppVtbl;

    interface IApp
    {
        CONST_VTBL struct IAppVtbl *lpVtbl;
    };

    

#ifdef COBJMACROS


#define IApp_QueryInterface(This,riid,ppvObject)	\
    ( (This)->lpVtbl -> QueryInterface(This,riid,ppvObject) ) 

#define IApp_AddRef(This)	\
    ( (This)->lpVtbl -> AddRef(This) ) 

#define IApp_Release(This)	\
    ( (This)->lpVtbl -> Release(This) ) 


#define IApp_GetTypeInfoCount(This,pctinfo)	\
    ( (This)->lpVtbl -> GetTypeInfoCount(This,pctinfo) ) 

#define IApp_GetTypeInfo(This,iTInfo,lcid,ppTInfo)	\
    ( (This)->lpVtbl -> GetTypeInfo(This,iTInfo,lcid,ppTInfo) ) 

#define IApp_GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId)	\
    ( (This)->lpVtbl -> GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId) ) 

#define IApp_Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr)	\
    ( (This)->lpVtbl -> Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr) ) 


#define IApp_CreateDWG(This,FullPath,MinX,MinY,MaxX,MaxY)	\
    ( (This)->lpVtbl -> CreateDWG(This,FullPath,MinX,MinY,MaxX,MaxY) ) 

#define IApp_GetData(This,DWGFullPath,LineLength,CurveLength,Square,RegionCount)	\
    ( (This)->lpVtbl -> GetData(This,DWGFullPath,LineLength,CurveLength,Square,RegionCount) ) 

#endif /* COBJMACROS */


#endif 	/* C style interface */




#endif 	/* __IApp_INTERFACE_DEFINED__ */



#ifndef __DSPLib_LIBRARY_DEFINED__
#define __DSPLib_LIBRARY_DEFINED__

/* library DSPLib */
/* [helpstring][version][uuid] */ 


EXTERN_C const IID LIBID_DSPLib;

EXTERN_C const CLSID CLSID_App;

#ifdef __cplusplus

class DECLSPEC_UUID("34B61572-D28C-4696-8AC9-CD38A6E006C1")
App;
#endif
#endif /* __DSPLib_LIBRARY_DEFINED__ */

/* Additional Prototypes for ALL interfaces */

unsigned long             __RPC_USER  BSTR_UserSize(     unsigned long *, unsigned long            , BSTR * ); 
unsigned char * __RPC_USER  BSTR_UserMarshal(  unsigned long *, unsigned char *, BSTR * ); 
unsigned char * __RPC_USER  BSTR_UserUnmarshal(unsigned long *, unsigned char *, BSTR * ); 
void                      __RPC_USER  BSTR_UserFree(     unsigned long *, BSTR * ); 

/* end of Additional Prototypes */

#ifdef __cplusplus
}
#endif

#endif


